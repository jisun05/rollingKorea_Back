package history.traveler.rollingkorea.comment.controller.response;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.service.CommentService;
import java.util.List;

public record CommentResponse(
        Long commentId,
        Long userId,
        String content
) {
    public CommentResponse(Comment comment) {
        this(comment.getCommentId(), comment.getUser().getUserId(),
                comment.getContent());

    }


    // Reply 리스트를 별도로 조회하는 메서드
    public List<ReplySearchResponse> getReplies(CommentService commentService) {
        return commentService.getRepliesByCommentId(this.commentId);
    }
}
