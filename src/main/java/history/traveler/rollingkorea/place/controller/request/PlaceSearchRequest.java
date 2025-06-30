package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

/**
 * Place 리스트를 areaCode 기준으로 필터링 조회할 때 사용하는 DTO.
 */
public record PlaceSearchRequest(
        @NotNull(message = "areaCode는 필수입니다.")
        Integer areaCode
) {}
