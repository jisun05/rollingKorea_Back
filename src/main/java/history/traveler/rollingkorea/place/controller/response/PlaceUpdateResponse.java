package history.traveler.rollingkorea.place.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.place.domain.Image;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PlaceUpdateResponse(
        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        List<Image> imageList,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) { }
