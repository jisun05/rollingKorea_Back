package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.question.domain.ContactUs;

import java.time.LocalDateTime;

public record ContactUsEditResponse(
        Long contactUsId,  // 문의 ID
        String content,    // 수정된 내용
        Long parentId,     // 부모 ID
        Long listOrder,    // 리스트 순서
        LocalDateTime updatedAt // 수정된 시간
) {
    // Static factory method to create ContactUsEditResponse from ContactUs entity
    public static ContactUsEditResponse from(ContactUs contactUs) {
        return new ContactUsEditResponse(
                contactUs.getContactUsId(),
                contactUs.getContent(),
                contactUs.getParentId(),
                contactUs.getListOrder(),
                contactUs.getUpdatedAt()
        );
    }
}
