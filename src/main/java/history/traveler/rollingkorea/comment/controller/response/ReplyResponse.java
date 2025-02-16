package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.user.controller.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record ReplyResponse(
        String content, UserResponse user, LocalDateTime createdAt, LocalDateTime updatedAt, Long likes
) {
    public ReplyResponse(Reply reply) {
        this(reply.getContent(),
                new UserResponse(reply.getUser(), List.of()), // ✅ 기존 UserResponse 사용
                reply.getCreatedAt(),
                reply.getUpdatedAt(),
                reply.getLikes());
    }
}
