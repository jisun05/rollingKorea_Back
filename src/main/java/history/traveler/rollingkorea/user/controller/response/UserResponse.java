package history.traveler.rollingkorea.user.controller.response;

import history.traveler.rollingkorea.user.domain.LoginType;
import history.traveler.rollingkorea.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long userId,
        String loginId,
        String userName,
        String nickName,
        String location,
        LocalDateTime birthday,
        String phoneNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LoginType loginType,
        List<String> roles // 권한 필드 추가
        ) {
    public UserResponse(User user, List<String> roles) {
        this(user.getUserId(), user.getLoginId(), user.getUserName(), user.getNickname(), user.getLocation(),
                user.getBirthday(), user.getPhoneNumber(), user.getCreatedAt(), user.getUpdatedAt(),user.getLoginType(), roles);
    }




    public static UserResponse toResponse(User user, List<String> roles) {
        return new UserResponse(user, roles);
    }
}
