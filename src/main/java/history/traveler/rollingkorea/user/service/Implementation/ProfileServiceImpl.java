package history.traveler.rollingkorea.user.service.implementation;

import history.traveler.rollingkorea.user.controller.request.UpdateProfileRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static history.traveler.rollingkorea.user.controller.response.UserResponse.toResponse;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfile(String principalName) {
        User user = userRepository.findByLoginId(principalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // 엔티티의 RoleType을 기반으로 역할 목록 생성
        List<String> roles = List.of(user.getRoleType().name());
        return toResponse(user, roles);
    }

    @Override
    @Transactional
    public UserResponse updateUserFields(String principalName, UpdateProfileRequest req) {
        User user = userRepository.findByLoginId(principalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (req.userName() != null)     user.setUserName(req.userName());
        if (req.nickname()  != null)     user.setNickname(req.nickname());
        if (req.location()  != null)     user.setLocation(req.location());
        if (req.mobile()    != null)     user.setPhoneNumber(req.mobile());
        if (req.birthday()  != null) {
            LocalDate ld = LocalDate.parse(req.birthday());
            user.setBirthday(ld.atStartOfDay());
        }
        user.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        List<String> roles = List.of(saved.getRoleType().name());
        return toResponse(saved, roles);
    }

    @Override
    @Transactional
    public void deleteUser(String principalName) {
        User user = userRepository.findByLoginId(principalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.userDelete();
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
