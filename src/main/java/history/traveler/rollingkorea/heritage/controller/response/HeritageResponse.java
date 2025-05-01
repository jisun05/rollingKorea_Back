package history.traveler.rollingkorea.heritage.dto.response;

import history.traveler.rollingkorea.heritage.domain.Heritage;

public record HeritageResponse(
        String ccsiName,
        String ccbaCtcdNm,
        String imageUrl,
        String latitude,
        String longitude
) {
    public static HeritageResponse from(Heritage heritage) {
        return new HeritageResponse(
                heritage.getCcsiName(),
                heritage.getCcbaCtcdNm(),
                heritage.getImageUrl(),
                heritage.getLatitude(),
                heritage.getLongitude()
        );
    }
}
