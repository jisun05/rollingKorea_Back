package history.traveler.rollingkorea.question.controller.response;

import history.traveler.rollingkorea.question.domain.ContactUs;

import java.time.LocalDateTime;

public record ContactUsCreateResponse(
        Long contactUsId,
        String content,
        Long parentId,
        Long listOrder,
        FileResponse file,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    // Convert ContactUs entity to ContactUsCreateResponse
    public static ContactUsCreateResponse fromContactUs(ContactUs contactUs) {
        return new ContactUsCreateResponse(
                contactUs.getContactUsId(),
                contactUs.getContent(),
                contactUs.getParentId(),
                contactUs.getListOrder(),
                contactUs.getFile() != null ? FileResponse.fromFile(contactUs.getFile()) : null,
                contactUs.getCreatedAt(),
                contactUs.getUpdatedAt()
        );
    }
}
