package history.traveler.rollingkorea.place.controller.response;

import java.util.List;

public record UpdatePlaceResponse(

        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        List<ImageResponse> imageList

) {
}
