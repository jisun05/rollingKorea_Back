package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.Place;
import lombok.Builder;


import java.util.List;

@Builder
public record PlaceResponse(


        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        String countLike,
        List<ImageResponse> imageList
)
{


    public static PlaceResponse from(Place place) {
        return PlaceResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .region(place.getRegion())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .placeDescription(place.getPlaceDescription())
                .countLike(place.getCountLike())
                .imageList(ImageResponse.toResponse(place))
                .build();
    }

}