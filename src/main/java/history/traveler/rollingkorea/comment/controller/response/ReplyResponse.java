package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.user.controller.response.UserResponse;
import java.time.LocalDateTime;

public record ReplyResponse(
        String content, UserResponse user, LocalDateTime createdAt, LocalDateTime updatedAt, Long likes
) {
    public ReplyResponse(Reply reply) {
        this(reply.getContent(),
                new UserResponse(reply.getUser()), // ✅ 기존 UserResponse 사용
                reply.getCreatedAt(),
                reply.getUpdatedAt(),
                reply.getLikes());
    }
}
