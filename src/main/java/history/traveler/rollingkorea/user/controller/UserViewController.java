//package history.traveler.rollingkorea.user.controller;
//
//import history.traveler.rollingkorea.place.controller.PlaceController;
//import history.traveler.rollingkorea.user.controller.request.UserEditRequest;
//import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
//import history.traveler.rollingkorea.user.controller.response.UserResponse;
//import history.traveler.rollingkorea.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor //클래스의 모든 final 필드와 @NonNull로 표시된 필드를 매개변수로 받는 생성자를 자동으로 생성
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000/*")
//public class UserViewController {
//
//    private final UserService userService;
//    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
//
////
////    @CrossOrigin(origins = "http://localhost:3000")
////    @PostMapping("/googleLoginUrl")
////    public ResponseEntity<?> login(@RequestBody AddUserRequest addUserRequest) {
////        // 사용자 인증 로직
////        String email = addUserRequest.getEmail();
////        String password = addUserRequest.getPassword();
////        log.info("email = " + email + "CHECK /googleLoginUrl");
////
////        // 사용자 인증
////        User user = userService.authenticate(email, password);
////        if (user == null) {
////            // 인증 실패 시 401 Unauthorized 반환
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 잘못된 이메일 또는 비밀번호입니다.");
////        }
////
////        // JWT 토큰 생성
////        String token = tokenProvider.generateToken(user, Duration.ofHours(1)); // 1시간 유효한 토큰 생성
////
////        // 성공 시 JWT 토큰 반환
////        return ResponseEntity.ok(new CreateAccessTokenResponse(token));
////    }
//
//    //from login
////    @PostMapping("/user/login")
////    @ResponseStatus(HttpStatus.OK)
////    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
////        return userService.login(loginRequest);
////    }
//
//    //sign up
//    @PostMapping("/user/signup")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<String> signup(@RequestBody @Valid UserSignupRequest userSignupRequest){
//        logger.info("Request SignUP"); // 응답 로그 추가
//        userService.userSignup(userSignupRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS");
//    }
//
//    @GetMapping("/user/me")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')") //메서드에 대한 접근 제어를 설정, 사용자가 'USER' 또는 'ADMIN' 역할이여야 접근 가능
//    @ResponseStatus(HttpStatus.OK)
//    public UserResponse findByDetailMyInfo() {
//        return userService.findByDetailMyInfo();
//    }
//    //edit user
//    @PutMapping("/user")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public void memberEdit(@RequestBody @Valid UserEditRequest userEditRequest) {
//        userService.userEdit(userEditRequest);
//    }
//
//    //withdrawal
//    @DeleteMapping("/user")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public void userDelete() {
//        userService.userDelete();
//    }
//
////
////    @GetMapping("/logout")
////    public String logout(HttpServletRequest request, HttpServletResponse response) {
////        new SecurityContextLogoutHandler().logout(request, response,
////                SecurityContextHolder.getContext().getAuthentication());
////        return "redirect:/";
////    }
//
//
//}
