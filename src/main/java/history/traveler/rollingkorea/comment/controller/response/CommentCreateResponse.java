package history.traveler.rollingkorea.comment.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentCreateResponse(
        Long commentId,
        Long userId,
        String nickname,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt,
        Long likes
) {
    public static CommentCreateResponse from(Comment comment) {
        return new CommentCreateResponse(
                comment.getCommentId(),
                comment.getUser().getUserId(),
                comment.getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes()
        );
    }
}
