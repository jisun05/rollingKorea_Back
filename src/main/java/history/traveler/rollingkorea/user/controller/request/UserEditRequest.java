package history.traveler.rollingkorea.user.controller.request;
import jakarta.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UserEditRequest(

        @NotNull(message = "Please enter your First Name.")
        String userName,              // Password

        @NotNull(message = "Please enter your password.")
        String password,              // Password

        @Nullable
        String gender,

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