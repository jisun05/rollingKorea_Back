package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;

import java.time.LocalDateTime;

public record ReplyCreateResponse(
        Long replyId,
        Long commentId,
        Long userId,
        String nickname,
        String content,
        LocalDateTime createdAt
) {
    public ReplyCreateResponse(Reply reply) {
        this(
                reply.getReplyId(),
                reply.getComment().getCommentId(),
                reply.getUser().getUserId(),
                reply.getNickname(),
                reply.getContent(),
                reply.getCreatedAt()
        );
    }
}
