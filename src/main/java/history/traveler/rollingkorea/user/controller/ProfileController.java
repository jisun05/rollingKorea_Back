package history.traveler.rollingkorea.user.controller;

import history.traveler.rollingkorea.user.controller.request.UpdateProfileRequest;
import history.traveler.rollingkorea.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    /**
     * Retrieve the authenticated user's profile
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(Principal principal) {
        // principal.getName() returns username (email or userId based on config)
        UserResponse response = profileService.getUserProfile(principal.getName());
        return ResponseEntity.ok(response);
    }

    /**
     * Update one or more fields of the authenticated user's profile
     */

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            Principal principal,
            @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        UserResponse updated = profileService.updateUserFields(principal.getName(), updateProfileRequest);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete (withdraw) the authenticated user's account
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(Principal principal) {
        profileService.deleteUser(principal.getName());
        return ResponseEntity.noContent().build();
    }




    /**
     * DTO for profile response
     */
    public static record UserResponse(
            Long id,
            String email,
            String firstName,
            String nickname,
            String location,
            String mobile,
            String birthday
    ) { }
}
