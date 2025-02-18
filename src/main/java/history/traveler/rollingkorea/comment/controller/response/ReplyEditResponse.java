package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;

import java.time.LocalDateTime;

public record ReplyEditResponse(
        Long replyId,
        Long commentId,
        String nickname,
        String content,
        Long likes,
        LocalDateTime updatedAt
) {

    // Reply 엔티티를 기반으로 ReplyEditResponse 생성하는 팩토리 메서드
    public ReplyEditResponse(Reply reply) {
        this(
                reply.getReplyId(),
                reply.getComment().getCommentId(),
                reply.getNickname(),
                reply.getContent(),
                reply.getLikes(),
                reply.getUpdatedAt()
        );
    }
}
