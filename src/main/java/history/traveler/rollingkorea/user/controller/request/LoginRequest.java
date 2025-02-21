package history.traveler.rollingkorea.user.controller.request;

import history.traveler.rollingkorea.user.domain.LoginType;
import jakarta.validation.constraints.NotNull;


public record LoginRequest(
        @NotNull(message = "The loginId is a required field.")
        String loginId,

        @NotNull(message = "The password is a required field.")
        String password,

        @NotNull(message = "The login type is a required field.")
        LoginType loginType // NO_SOCIAL , GOOGLE

) {

}
