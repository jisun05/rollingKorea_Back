package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;

import java.time.LocalDateTime;
import java.util.List;

public record ReplySearchResponse(
        Long replyId,
        Long userId,
        String nickname,
        String content,
        Long likes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // Reply 엔티티를 기반으로 ReplyResponse를 생성하는 팩토리 메서드
    public ReplySearchResponse(Reply reply) {
        this(
                reply.getReplyId(),
                reply.getUser() != null ? reply.getUser().getUserId() : null,
                reply.getNickname(),
                reply.getContent(),
                reply.getLikes(),
                reply.getCreatedAt(),
                reply.getUpdatedAt()
        );
    }

    // List<Reply> -> List<ReplyResponse> 변환하는 메서드
    public static List<ReplySearchResponse> fromList(List<Reply> replies) {
        return replies.stream().map(ReplySearchResponse::new).toList();
    }
}
