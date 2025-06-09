package history.traveler.rollingkorea.user.controller;

import history.traveler.rollingkorea.user.controller.request.UpdateProfileRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    private String extractEmail(Authentication authentication) {
        OAuth2AuthenticationToken oauth2 = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauth2.getPrincipal();
        return user.getAttribute("email");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        String email = extractEmail(authentication);
        UserResponse response = profileService.getUserProfile(email);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        String email = extractEmail(authentication);
        UserResponse updated = profileService.updateUserFields(email, updateProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(Authentication authentication) {
        String email = extractEmail(authentication);
        profileService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
