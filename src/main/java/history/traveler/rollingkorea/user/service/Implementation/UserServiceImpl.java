package history.traveler.rollingkorea.user.service.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import history.traveler.rollingkorea.global.config.security.JwtTokenDto;
import history.traveler.rollingkorea.global.config.security.TokenProvider;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.user.controller.request.LoginRequest;
import history.traveler.rollingkorea.user.controller.request.UserEditRequest;
import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.domain.RoleType;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserLoginHistoryRepository;
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static history.traveler.rollingkorea.global.error.ErrorCode.DUPLICATED_LOGIN_ID;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional //Apply transaction to all methods of this class.
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final LikePlaceRepository likePlaceRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public void userSignup(UserSignupRequest userSignupRequest) {
        DuplicatedLoginIdCheck(userRepository.findByLoginId(userSignupRequest.loginId()).isPresent());

        User user = User.create(userSignupRequest, passwordEncoder);
        userRepository.save(user);

    }

    @Override
    public void loginIdDuplicateCheck(String loginId) {
        DuplicatedLoginIdCheck(userRepository.findByLoginId(loginId).isPresent());
    }

    @Override
    public JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException {
             //TODO : after make JwtTokenDto,TokenProvider
        return null;
//        //if google
//        if (loginRequest.loginType().equals(LoginType.GOOGLE)) {
//            //make google user
//            User user = User.googleCreate(loginRequest, passwordEncoder);
//
//            // 동일한 Email , LoginID 일 경우 토큰만 발급 후 리턴
//            if (userRepository.findByLoginIdAndLoginType(loginRequest.loginId(),LoginType.NO_SOCIAL).isPresent()) {
//                return tokenProvider.generateToken(loginRequest);
//            }
//
//            DuplicatedLoginIdCheck(userRepository.findByLoginId(loginRequest.loginId()).isPresent());
//            return tokenProvider.generateToken(loginRequest);
//        }
//        // 로그인한 회원의 타입이 NO_SOCIAL 이 아니라면 예외 (로그인타입 GOOGLE  조건문으로 확인완료)
//        User user = userRepository.findByLoginId(loginRequest.loginId()).orElseThrow(
//                () -> new BusinessException(NOT_FOUND_USER));
//
//        // 비밀번호 일치 여부 비교 ( 로그인 요청한 PW == DB 암호화 비밀번호 )
//        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
//            throw new BusinessException(NOT_EQUAL_PASSWORD);
//        }
//        return tokenProvider.generateToken(loginRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByDetailMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증 정보 가져오기
        User user = userRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER)); // 회원 조회

        UserResponse userResponse = UserResponse.toResponse(user); // 회원 응답 DTO 생성

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // 권한 정보 가져오기
        List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList(); // 권한 리스트 생성
        userResponse.setRoles(list); // 응답 DTO에 권한 설정

        return userResponse; // 회원 정보 응답
    }


    @Override
    public void userEdit(UserEditRequest userEditRequest) {
        User user = getUser();
        user.update(userEditRequest, passwordEncoder);
    }


    @Override
    public void userDelete() {
        User user = getUser();
        user.userDelete();

        //Check RoleType
        if (user.getRoleType().equals(RoleType.ROLE_USER)) {
            List<LikePlace> likePlaceList = likePlaceRepository.findByUser_UserId(user.getUserId());
            likePlaceRepository.deleteAll(likePlaceList);
        }
    }

    @Override
    @Scheduled(cron = "0 0 00 * * *")
    public void schedulerLoginHistoryDeleteCron() {
        userLoginHistoryRepository.deleteAll();
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new BusinessException(NOT_FOUND_USER));

    }

    // 로그인 아이디 중복 체크
    private void DuplicatedLoginIdCheck(boolean duplicatedCheck) {
        if (duplicatedCheck) throw new BusinessException(DUPLICATED_LOGIN_ID);
    }
}
