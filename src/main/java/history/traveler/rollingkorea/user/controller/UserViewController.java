//package history.traveler.rollingkorea.user.controller;
//
//import history.traveler.rollingkorea.global.config.secutiry.token.CreateAccessTokenResponse;
//import history.traveler.rollingkorea.global.config.secutiry.token.TokenProvider;
//import history.traveler.rollingkorea.user.domain.User;
//import history.traveler.rollingkorea.user.repository.UserRepository;
//import history.traveler.rollingkorea.user.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.Duration;
//
//@Slf4j
//@RestController
//@Controller
//
//public class UserViewController {
//
//    private final UserService userService;
//    private final TokenProvider tokenProvider;
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    public UserViewController(UserService userService, TokenProvider tokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.userService = userService;
//        this.tokenProvider = tokenProvider;
//        this.passwordEncoder = passwordEncoder; // PasswordEncoder 초기화
//        this.userRepository = userRepository; // UserRepository 초기화
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/googleLoginUrl")
//    public ResponseEntity<CreateAccessTokenResponse> login(@RequestBody AddUserRequest addUserRequest) {
//        // 사용자 인증 로직
//        String email = addUserRequest.getEmail();
//        String password = addUserRequest.getPassword();
//        log.info("email = " + email + "CHECK /googleLoginUrl");
//
//        // 사용자 인증
//        User user = userService.authenticate(email, password);
//        if (user == null) {
//            // 인증 실패 시 401 Unauthorized 반환
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        // JWT 토큰 생성
//        String token = tokenProvider.generateToken(user, Duration.ofHours(1)); // 1시간 유효한 토큰 생성
//
//        // 성공 시 JWT 토큰 반환
//        return ResponseEntity.ok(new CreateAccessTokenResponse(token));
//    }
//
//    @PostMapping("/join")
//    public String join(User user) {
//        user.setEnabled("ROLE_USER");
//        String rawPassword = user.getPassword();
//        String encPassword = passwordEncoder.encode(rawPassword);
//        user.setPassword(encPassword);
//        userRepository.save(user); // 회원가입 잘됨. 비밀번호:1234 => 시큐리티로 로그인을 할 수 없음. 이유는 패스워드가 암호화가 안되었기 때문
//        return "redirect:/loginForm";
//    }
//
//    @PostMapping("/user")
//    public String signup(AddUserRequest request) {
//        userService.save(request);
//        return "redirect:/";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/";
//    }
//}
