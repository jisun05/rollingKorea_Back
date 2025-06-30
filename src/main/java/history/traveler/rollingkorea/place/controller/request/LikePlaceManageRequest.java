package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

/**
 * Like/Unlike 기능을 위한 요청 DTO.
 * Place PK가 이제 contentId로 바뀌었습니다.
 */
public record LikePlaceManageRequest(
        @NotNull(message = "contentId는 필수입니다.")
        Long contentId
) {}
