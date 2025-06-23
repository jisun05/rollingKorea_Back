package history.traveler.rollingkorea.user.service.Implementation;

import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.user.domain.LoginType;
import history.traveler.rollingkorea.user.domain.RoleType;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserLoginHistoryRepository;
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static history.traveler.rollingkorea.global.error.ErrorCode.DUPLICATED_LOGIN_ID;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final LikePlaceRepository likePlaceRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public User findOrCreateGoogleUser(String email, String name) {
        Optional<User> optionalUser = userRepository.findByLoginId(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getDeletedAt() != null) {
                // 🔁 탈퇴한 유저 재가입 처리
                user.setDeletedAt(null);
                user.setUpdatedAt(LocalDateTime.now());

                if (user.getNickname() == null) {
                    user.setNickname(generateDefaultNickname(name));
                }

                return userRepository.save(user);
            }

            return user;
        }

        // 🆕 신규 유저
        User newUser = User.builder()
                .loginId(email)
                .nickname(generateDefaultNickname(name))
                .loginType(LoginType.GOOGLE)
                .roleType(RoleType.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }

    private String generateDefaultNickname(String name) {
        return name + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByLoginId(email)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    public void loginIdDuplicateCheck(String userId) {
        DuplicatedLoginIdCheck(userRepository.findByUserId(Long.valueOf(userId)).isPresent());
    }

    @Transactional
    public void userDelete() {
        User user = getUser();
        user.userDelete();
        userRepository.save(user);

        if (user.getRoleType().equals(RoleType.ROLE_USER)) {
            List<LikePlace> likePlaceList = likePlaceRepository.findByUser_UserId(user.getUserId());
            likePlaceRepository.deleteAll(likePlaceList);
        }
    }

    @Override
    @Scheduled(cron = "0 0 00 * * *")
    public void schedulerLoginHistoryDeleteCron() {
        userLoginHistoryRepository.deleteAll();
    }

    // 🔐 현재 로그인 유저 조회
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    // 중복 체크
    private void DuplicatedLoginIdCheck(boolean duplicatedCheck) {
        if (duplicatedCheck) throw new BusinessException(DUPLICATED_LOGIN_ID);
    }
}
