package history.traveler.springbootdeveloper.controller;
import history.traveler.springbootdeveloper.dto.AddUserRequest;
import history.traveler.springbootdeveloper.dto.CreateAccessTokenResponse;
import history.traveler.springbootdeveloper.service.UserService; // 사용자 인증을 위한 서비스
import history.traveler.springbootdeveloper.config.jwt.TokenProvider;
import history.traveler.springbootdeveloper.domain.User; // 사용자 모델
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@Controller
public class UserViewController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    public UserViewController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/googleLoginUrl")
    public ResponseEntity<?> login(@RequestBody AddUserRequest addUserRequest) {
        // 사용자 인증 로직
        String email = addUserRequest.getEmail();
        String password = addUserRequest.getPassword();

        // 사용자 인증
        User user = userService.authenticate(email, password);
        if (user == null) {
            // 인증 실패 시 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 잘못된 이메일 또는 비밀번호입니다.");
        }

        // JWT 토큰 생성
        String token = tokenProvider.generateToken(user, Duration.ofHours(1)); // 1시간 유효한 토큰 생성

        // 성공 시 JWT 토큰 반환
        return ResponseEntity.ok(new CreateAccessTokenResponse(token));
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/googleLoginUrl")
//    public String signup() {
//        return "signup"; // 회원가입 페이지 반환
//    }


}
