package history.traveler.rollingkorea.user.service.implementation;
import history.traveler.rollingkorea.user.controller.ProfileController.UserResponse;
import history.traveler.rollingkorea.user.controller.request.UpdateProfileRequest;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfile(String principalName) {
        User user = userRepository.findByLoginId(principalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserFields(String principalName, UpdateProfileRequest req) {
        User user = userRepository.findByLoginId(principalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (req.userName() != null) user.setUserName(req.userName());
        if (req.nickname()  != null) user.setNickname(req.nickname());
        if (req.location()  != null) user.setLocation(req.location());
        if (req.mobile()    != null) user.setPhoneNumber(req.mobile());
        if (req.birthday()  != null) {
            LocalDate ld = LocalDate.parse(req.birthday());
            user.setBirthday(ld.atStartOfDay());
        }
        user.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        return toResponse(saved);
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

    /** 엔티티 → Controller.UserResponse 로 매핑 */
    private UserResponse toResponse(User u) {
        return new UserResponse(
                u.getUserId(),
                u.getLoginId(),
                u.getUserName(),
                u.getNickname(),
                u.getLocation(),
                u.getPhoneNumber(),
                u.getBirthday() != null
                        ? u.getBirthday().toLocalDate().toString()
                        : null
        );
    }
}
