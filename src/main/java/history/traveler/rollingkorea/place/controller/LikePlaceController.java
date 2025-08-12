// src/main/java/history/traveler/rollingkorea/place/controller/LikePlaceController.java
package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like-places")
@RequiredArgsConstructor
public class LikePlaceController {

    private final LikePlaceService likePlaceService;
    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Toggle like on a place", description = "Like or unlike a place for the current user.")
    public void manageLikePlace(
            @AuthenticationPrincipal DefaultOAuth2User oauth2User,
            @RequestBody @Valid LikePlaceManageRequest req
    ) {
        // OAuth2User에서 이메일 가져오기
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByLoginId(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found in DB"));

        likePlaceService.manageLikePlace(user.getUserId(), req);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List liked places", description = "Get paged list of places the user has liked.")
    public Page<LikePlaceResponse> findAllByUser(
            @AuthenticationPrincipal DefaultOAuth2User oauth2User,
            Pageable pageable
    ) {
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByLoginId(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found in DB"));

        return likePlaceService.findAllByUser(user.getUserId(), pageable);
    }
}
