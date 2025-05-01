package history.traveler.rollingkorea.heritage.dto.request;

import history.traveler.rollingkorea.heritage.domain.Heritage;

public record HeritageRequest(
        String ccsiName,
        String ccbaCtcdNm,
        String imageUrl,
        String latitude,
        String longitude
) {
    public Heritage toEntity() {
        return new Heritage(ccsiName, ccbaCtcdNm, imageUrl, latitude, longitude);
    }
}
