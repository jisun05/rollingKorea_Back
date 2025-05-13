package history.traveler.rollingkorea.user.service;


import history.traveler.rollingkorea.user.controller.ProfileController.UserResponse;
import history.traveler.rollingkorea.user.controller.request.UpdateProfileRequest;

/**
 * ProfileController 에서 호출하는 시그니처에 맞춰 메서드를 선언합니다.
 */
public interface ProfileService {
    UserResponse getUserProfile(String principalName);
    UserResponse updateUserFields(String principalName, UpdateProfileRequest req);
    void deleteUser(String principalName);
}
