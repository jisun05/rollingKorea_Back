package history.traveler.rollingkorea.user.service;


import history.traveler.rollingkorea.user.domain.User;

public interface UserService {


    // 내정보찾기
    //UserResponse findByDetailMyInfo();

    // 회원생성
    //void userSignup(UserSignupRequest userSignupRequest);

    // 회원 중복체크
    //void loginIdDuplicateCheck(String loginId);

    // 일반 로그인                               //자바 객체 -> JSON 문자열
    // JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException;

    // 회원 수정
    //void userEdit(UserEditRequest userEditRequest);

    // 회원 삭제
    //void userDelete();
    User findOrCreateGoogleUser(String email, String name);
    // 로그인 히스토리 삭제 스케줄러
    void schedulerLoginHistoryDeleteCron();
}
