package history.traveler.rollingkorea.place.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.place.domain.Place;
import java.time.LocalDateTime;

public record PlaceCreateResponse(
        Long placeId,
        String placeName,
        String website,
        double latitude,
        double longitude,
        String region,
        String placeDescription,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime updatedAt
) {
    public static PlaceCreateResponse from(Place place) {
        return new PlaceCreateResponse(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getWebsite(),
                place.getLatitude(),
                place.getLongitude(),
                place.getRegion(),
                place.getPlaceDescription(),
                place.getCreatedAt(),
                place.getUpdatedAt()
        );
    }
}
