package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

/**
 * 사용자가 Place 정보 일부(제목, 주소 등)를 수정할 때 사용하는 DTO.
 * 엔티티의 update() 메서드와 매핑됩니다.
 */
public record PlaceEditRequest(
        @NotNull(message = "제목(title)은 필수입니다.")
        String title,

        @NotNull(message = "주소1(addr1)은 필수입니다.")
        String addr1,

        @NotNull(message = "주소2(addr2)은 필수입니다.")
        String addr2
) {}
