package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentEditResponse(
        Long commentId,
        String content,
        LocalDateTime updatedAt
) {

    // Comment 엔티티를 기반으로 CommentEditResponse를 생성하는 팩토리 메서드
    public static CommentEditResponse from(Comment comment) {
        return new CommentEditResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUpdatedAt()
        );
    }
}
