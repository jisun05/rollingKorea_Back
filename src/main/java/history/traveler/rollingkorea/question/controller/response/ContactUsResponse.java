package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.question.domain.ContactUs;

import java.util.stream.Collectors;

public record ContactUsResponse(
        Long userId,
        String content
) {
    public ContactUsResponse(ContactUs contactUs) {
        this(contactUs.getUser().getUserId(),
                contactUs.getContent());
    }
}
