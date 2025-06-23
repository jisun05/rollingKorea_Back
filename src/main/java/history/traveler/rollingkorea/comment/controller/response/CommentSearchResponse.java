package history.traveler.rollingkorea.comment.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentSearchResponse(
        Long commentId,
        String nickname,
        String content,

        // 날짜를 ISO-8601 문자열로 직렬화
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt,

        Long likes
) {
    public CommentSearchResponse(Comment comment) {
        this(
                comment.getCommentId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes()
        );
    }
}
