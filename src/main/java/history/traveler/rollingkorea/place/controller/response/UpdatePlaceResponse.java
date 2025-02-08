package history.traveler.rollingkorea.place.controller.response;
import history.traveler.rollingkorea.place.domain.Image;
import java.util.List;
public record UpdatePlaceResponse(

        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        List<Image> imageList

) {
}
