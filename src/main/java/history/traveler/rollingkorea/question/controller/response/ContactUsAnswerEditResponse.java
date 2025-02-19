package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.question.domain.ContactUs;

import java.time.LocalDateTime;

public record ContactUsAnswerEditResponse(
        Long contactUsId,  // 문의 ID
        String content,    // 수정된 내용
        Long parentId,     // 부모 ID
        Long listOrder,    // 리스트 순서
        byte[] fileData,   // 수정된 파일 데이터
        String fileName,   // 수정된 파일 이름
        LocalDateTime updatedAt // 수정된 시간
) {
    // ContactUs 엔티티로부터 ContactUsEditResponse를 생성하는 팩토리 메서드
    public static ContactUsAnswerEditResponse from(ContactUs contactUs) {
        return new ContactUsAnswerEditResponse(
                contactUs.getContactUsId(),
                contactUs.getContent(),
                contactUs.getParentId(),
                contactUs.getListOrder(),
                contactUs.getFile() != null ? contactUs.getFile().getFileData() : null,
                contactUs.getFile() != null ? contactUs.getFile().getFileName() : null,
                contactUs.getUpdatedAt()
        );
    }
}

