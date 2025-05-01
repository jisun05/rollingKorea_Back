package history.traveler.rollingkorea.user.service.Implementation;

import history.traveler.rollingkorea.global.config.security.TokenProvider;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.user.domain.RoleType;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserLoginHistoryRepository;
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static history.traveler.rollingkorea.global.error.ErrorCode.DUPLICATED_LOGIN_ID;

@Service
@RequiredArgsConstructor
@Transactional //Apply transaction to all methods of this class.
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    //private final PasswordEncoder passwordEncoder;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final LikePlaceRepository likePlaceRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;



    @Override
    public User findOrCreateGoogleUser(String email, String name) {
        return userRepository.findByLoginId(email).orElseGet(() -> {
            User newUser = User.builder()
                    .loginId(email)
                    .nickname(name)
                    .roleType(RoleType.ROLE_USER)
                    .build();
            return userRepository.save(newUser);
        });
    }


//    @Override
//    public void userSignup(UserSignupRequest userSignupRequest) {
//        DuplicatedLoginIdCheck(userRepository.findByLoginId(userSignupRequest.loginId()).isPresent());
//
//        User user = User.create(userSignupRequest, passwordEncoder);
//        userRepository.save(user);
//
//
//    }

    //@Override
    public void loginIdDuplicateCheck(String userId) {
        DuplicatedLoginIdCheck(userRepository.findByUserId(Long.valueOf(userId)).isPresent());
    }

    @Transactional
    //@Override
    public void userDelete() {
        User user = getUser();
        user.userDelete();
        userRepository.save(user);

        //Check RoleType
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

    private User getUser() {
        // 🔥 테스트용 더미 유저 추가 (로그인 없이 Swagger 테스트 가능)
        return User.builder()
                .userId(1L)
                .loginId("jisunnala@gmail.com")
                .nickname("TestUser")
                .roleType(RoleType.ROLE_USER)
                .build();
    }
    
    //테스트를 위한 주석

//    private User getUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String loginId = authentication.getName();
//
//        return userRepository.findByUserId(userId).orElseThrow(
//                () -> new BusinessException(NOT_FOUND_USER));
//
//    }

    // 로그인 아이디 중복 체크
    private void DuplicatedLoginIdCheck(boolean duplicatedCheck) {
        if (duplicatedCheck) throw new BusinessException(DUPLICATED_LOGIN_ID);
    }
}
