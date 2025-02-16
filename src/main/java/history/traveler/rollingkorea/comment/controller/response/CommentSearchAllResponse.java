package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Comment;

public record CommentSearchAllResponse(
        Long commentId,
        String nickname,
        String content
) {
    public CommentSearchAllResponse(Comment comment) {
        this(comment.getCommentId(),
                comment.getNickname(),
                comment.getContent());

    }
}
