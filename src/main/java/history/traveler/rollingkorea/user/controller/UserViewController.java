package history.traveler.rollingkorea.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import history.traveler.rollingkorea.global.config.security.JwtTokenDto;
import history.traveler.rollingkorea.user.controller.request.LoginRequest;
import history.traveler.rollingkorea.user.controller.request.UserEditRequest;
import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor //클래스의 모든 final 필드와 @NonNull로 표시된 필드를 매개변수로 받는 생성자를 자동으로 생성
@RequestMapping("/api")
public class UserViewController {

    private final UserService userService;

//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/googleLoginUrl")
//    public ResponseEntity<?> login(@RequestBody AddUserRequest addUserRequest) {
//        // 사용자 인증 로직
//        String email = addUserRequest.getEmail();
//        String password = addUserRequest.getPassword();
//        log.info("email = " + email + "CHECK /googleLoginUrl");
//
//        // 사용자 인증
//        User user = userService.authenticate(email, password);
//        if (user == null) {
//            // 인증 실패 시 401 Unauthorized 반환
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 잘못된 이메일 또는 비밀번호입니다.");
//        }
//
//        // JWT 토큰 생성
//        String token = tokenProvider.generateToken(user, Duration.ofHours(1)); // 1시간 유효한 토큰 생성
//
//        // 성공 시 JWT 토큰 반환
//        return ResponseEntity.ok(new CreateAccessTokenResponse(token));
//    }

    //from login
    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        return userService.login(loginRequest);
    }

    //sign up
    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody @Valid UserSignupRequest userSignupRequest){
        userService.userSignup(userSignupRequest);
        return "redirect:/";
    }

    @GetMapping("/members/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") //메서드에 대한 접근 제어를 설정, 사용자가 'USER' 또는 'ADMIN' 역할이여야 접근 가능
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findByDetailMyInfo() {
        return userService.findByDetailMyInfo();
    }
    //edit user
    @PutMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void memberEdit(@RequestBody @Valid UserEditRequest userEditRequest) {
        userService.userEdit(userEditRequest);
    }

    //withdrawal
    @DeleteMapping("/member")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void userDelete() {
        userService.userDelete();
    }

//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/";
//    }


}
