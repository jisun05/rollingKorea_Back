package history.traveler.rollingkorea.user.service.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import history.traveler.rollingkorea.global.config.security.JwtTokenDto;
import history.traveler.rollingkorea.user.controller.request.LoginRequest;
import history.traveler.rollingkorea.user.controller.request.UserEditRequest;
import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import history.traveler.rollingkorea.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse findByDetailMyInfo() {
        return null;
    }

    @Override
    public void userSignup(UserSignupRequest userSignupRequest) {

    }


    @Override
    public JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException {
        return null;
    }

    @Override
    public void userEdit(UserEditRequest userEditRequest) {

    }

    @Override
    public void userDelete() {

    }

    @Override
    public void schedulerLoginHistoryDeleteCron() {

    }
}
