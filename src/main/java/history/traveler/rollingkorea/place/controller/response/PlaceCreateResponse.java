package history.traveler.rollingkorea.place.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.place.domain.Place;

import java.time.LocalDateTime;

public record PlaceCreateResponse(
        Long contentId,
        String title,
        String addr1,
        String addr2,
        Double mapX,
        Double mapY,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime modifiedTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime importedAt
) {
    public static PlaceCreateResponse from(Place place) {
        return new PlaceCreateResponse(
                place.getContentId(),
                place.getTitle(),
                place.getAddr1(),
                place.getAddr2(),
                place.getMapX(),
                place.getMapY(),
                place.getCreatedTime(),
                place.getModifiedTime(),
                place.getImportedAt()
        );
    }
}
