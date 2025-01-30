package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.Place;

import java.util.List;

public record PlaceResponse(


        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        List<ImageResponse> imageList
)
{

    public PlaceResponse(Place place){
        this(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getRegion(),
                place.getPlaceDescription(),
                place.getLatitude(),
                place.getLongitude(),
                ImageResponse.toResponse(place)
        );
    }

}