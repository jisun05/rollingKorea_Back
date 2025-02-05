package history.traveler.rollingkorea.comment.controller.response;
import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.user.domain.User;
import java.time.LocalDateTime;

public record ReplyResponse(
        String content, User user, LocalDateTime createdAt, LocalDateTime updatedAt, Long likes
) {
    public ReplyResponse(Reply reply) {
        this(reply.getContent(),
                reply.getUser(),
                reply.getCreatedAt(),
                reply.getUpdatedAt(),
                reply.getLikes());
    }
}
