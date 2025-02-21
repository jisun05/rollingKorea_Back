package history.traveler.rollingkorea.question.controller.response;
import history.traveler.rollingkorea.question.domain.ContactUs;

public record ContactUsAnswerResponse(
        Long parentId, // 원본 문의의 ID
        Long contactUsId,  // 관리자의 답변 레코드의 ID
        String content
) {
    public static ContactUsAnswerResponse from(Long parentId, ContactUs contactUs) {
        return new ContactUsAnswerResponse(parentId, contactUs.getContactUsId(), contactUs.getContent());
    }
}
