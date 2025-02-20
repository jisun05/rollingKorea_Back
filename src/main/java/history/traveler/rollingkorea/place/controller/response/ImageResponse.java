package history.traveler.rollingkorea.place.controller.response;
import history.traveler.rollingkorea.place.domain.Place;
import java.util.List;

public record ImageResponse(

        byte[] imagePath
        
) {

    public static List<ImageResponse> toResponse(Place place){
        //ImageServiceImpl의 findImagesByPlaceId 메소드로
        return List.of();  // TODO : TEST
    }
}
