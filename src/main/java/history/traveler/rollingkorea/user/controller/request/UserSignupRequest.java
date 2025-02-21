package history.traveler.rollingkorea.user.controller.request;


import history.traveler.rollingkorea.user.domain.RoleType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record UserSignupRequest(
        @NotNull(message = "Enter your login ID.")
        @Email
        String loginId,

        @NotNull(message = "Enter your password.")
        String password,

        @NotNull(message = "Enter your nickname.")
        String nickname,

        @NotNull(message = "Enter your First Name.")
        String userName,

        @Nullable
        String phoneNumber,

        @Nullable
        LocalDateTime birthday,

        @Nullable
        String location,

        @NotNull(message = "권한을 입력하세요.")
        List<RoleType> roles    // 권한타입
) {

}
