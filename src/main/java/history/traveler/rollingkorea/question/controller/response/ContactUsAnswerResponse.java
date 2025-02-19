package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.question.domain.ContactUs;

import java.time.LocalDateTime;

public record ContactUsAnswerResponse(
        Long contactUsId,    // ContactUs의 ID
        String content,      // ContactUs의 내용
        LocalDateTime createdAt,  // 생성 시간
        LocalDateTime updatedAt   // 수정 시간
) {
    // 정적 팩토리 메서드
    public static ContactUsAnswerResponse from(ContactUs contactUs) {
        return new ContactUsAnswerResponse(
                contactUs.getContactUsId(),
                contactUs.getContent(),
                contactUs.getCreatedAt(),
                contactUs.getUpdatedAt()
        );
    }
}
