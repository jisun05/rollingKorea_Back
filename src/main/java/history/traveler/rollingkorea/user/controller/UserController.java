package history.traveler.rollingkorea.user.controller;

import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    /**
     * 인증된 사용자 정보를 반환
     */
    @GetMapping("/user")
    public ResponseEntity<UserResponse> getCurrentUser(OAuth2AuthenticationToken auth) {
        OAuth2User oAuth2User = auth.getPrincipal();  // 인증된 사용자 정보 가져오기
        String email = (String) oAuth2User.getAttributes().get("email");

        // UserService를 통해 DB User 엔티티 찾기
        User user = userService.findByEmail(email);

        // Security 권한 목록 (ROLE_USER 등) 추출
        List<String> userRoles = auth.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        // UserResponse로 변환해 반환
        return ResponseEntity.ok(UserResponse.toResponse(user, userRoles));
    }
}
