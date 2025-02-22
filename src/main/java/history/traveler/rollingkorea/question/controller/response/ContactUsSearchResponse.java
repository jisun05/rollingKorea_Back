package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.question.domain.ContactUs;

import java.time.LocalDateTime;

public record ContactUsSearchResponse(
        Long contactUsId,      // ContactUs의 ID
        String content,        // ContactUs의 내용
        Long parentId,         // ContactUs의 parentId
        Long listOrder,        // ContactUs의 listOrder
        LocalDateTime createdAt, // 생성 시간
        LocalDateTime updatedAt// 수정 시간
) {

    // 정적 팩토리 메서드
    public static ContactUsSearchResponse from(ContactUs contactUs) {
        return new ContactUsSearchResponse(
                contactUs.getContactUsId(),
                contactUs.getContent(),
                contactUs.getParentId(),
                contactUs.getListOrder(),
                contactUs.getCreatedAt(),
                contactUs.getUpdatedAt()
        );
    }
}
