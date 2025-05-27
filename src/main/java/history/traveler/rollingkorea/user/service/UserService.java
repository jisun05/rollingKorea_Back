package history.traveler.rollingkorea.user.service;

import history.traveler.rollingkorea.user.domain.User;

public interface UserService {

    /**
     * 구글 OAuth2 로그인 시, 이메일로 기존 사용자를 조회하고
     * 없으면 새로 생성
     */
    User findOrCreateGoogleUser(String email, String name);

    /**
     * 세션 기반 인증 이후, 현재 로그인된 이메일로
     * 사용자 정보를 조회
     */
    User findByEmail(String email);

    /**
     * 로그인이력 삭제 스케줄러 (기존 유지)
     */
    void schedulerLoginHistoryDeleteCron();
}
