package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentSearchResponse(
        Long commentId,
        String nickname,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likes
) {

    // Comment 엔티티를 기반으로 CommentSearchAllResponse를 생성하는 팩토리 메서드
    public CommentSearchResponse(Comment comment) {
        this(
                comment.getCommentId(),
                comment.getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes()
        );
    }
}
