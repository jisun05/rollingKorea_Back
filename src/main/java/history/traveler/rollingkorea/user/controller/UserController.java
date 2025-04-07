package history.traveler.rollingkorea.user.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import history.traveler.rollingkorea.global.config.security.TokenProvider;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/google/login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        String idTokenString = request.idToken();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("386257786961-e3udpn75tlqvi29ejnkc3sagve80aqjf.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                Boolean emailVerified = (Boolean) payload.getEmailVerified();

                if (emailVerified == null || !emailVerified) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("구글에서 이메일 검증 실패");
                }

                User user = userService.findOrCreateGoogleUser(email, name);
                String token = tokenProvider.generateToken(user);
                return ResponseEntity.ok(new CreateAccessTokenResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 ID 토큰");
            }
        } catch (Exception e) {
            log.error("구글 로그인 중 예외 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 검증 오류");
        }
    }

    // record 형식 DTOs (패키지 분리 추천)
    public record GoogleLoginRequest(String idToken) {}
    public record CreateAccessTokenResponse(String token) {}
}
