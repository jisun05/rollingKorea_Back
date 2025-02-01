package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(

        Long userId,
        String content,
        List<ReplyResponse> replyResponse

) {

    public CommentResponse(Comment comment) {
        this(comment.getUser().getUserId(),
                comment.getContent(),
                comment.getReplies()
                        .stream()
                        .map(ReplyResponse::new)
                        .collect(Collectors.toList()));
    }
}
