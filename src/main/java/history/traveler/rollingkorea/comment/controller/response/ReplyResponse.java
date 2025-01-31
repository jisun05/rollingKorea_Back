package history.traveler.rollingkorea.comment.controller.response;

import history.traveler.rollingkorea.comment.domain.Reply;

public record ReplyResponse(String comment) {

    public ReplyResponse(Reply reply) {
        this(reply.getContent());
    }
}
