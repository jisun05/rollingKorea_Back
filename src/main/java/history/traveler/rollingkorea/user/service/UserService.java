package history.traveler.rollingkorea.user.service;


import history.traveler.rollingkorea.user.controller.request.UserEditRequest;
import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;


public interface UserService {


    // 내정보찾기
    UserResponse findByDetailMyInfo();

    // 회원생성
    void userSignup(UserSignupRequest userSignupRequest);

    // 회원 중복체크
    void loginIdDuplicateCheck(String loginId);

    // 일반 로그인                               //자바 객체 -> JSON 문자열
   // JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException;

    // 회원 수정
    void userEdit(UserEditRequest userEditRequest);

    // 회원 삭제
    void userDelete();

    // 로그인 히스토리 삭제 스케줄러
    void schedulerLoginHistoryDeleteCron();
}
