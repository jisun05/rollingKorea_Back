package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

/**
 * 외부 API를 통해 받아온 Heritage 데이터를
 * Place로 복사(import)할 때 사용하는 DTO.
 * 최소한 contentId만 넘겨주면 서비스 레이어에서 fetch→fromHeritage() 처리합니다.
 */
public record PlaceCreateRequest(
        @NotNull(message = "contentId는 필수입니다.")
        Long contentId
) {}
