package history.traveler.rollingkorea.user.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserEditRequest(

        @NotNull(message = "Please enter your First Name.")
        String userName,

        @NotNull(message = "Please enter your password.")
        String password,              // Password

        @Nullable
        String nickname,

        @Nullable
        String location,

        @Nullable
        LocalDateTime birthday,

        @NotNull(message = "Please enter your phone number.")
        String phoneNumber                 // Phone number
) {

}