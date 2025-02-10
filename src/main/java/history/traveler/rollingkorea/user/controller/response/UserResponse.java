package history.traveler.rollingkorea.user.controller.response;

import history.traveler.rollingkorea.user.domain.User;
import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String loginId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public UserResponse(User user) {
        this(user.getUserId(), user.getLoginId(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
