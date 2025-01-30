package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;

import java.util.ArrayList;
import java.util.List;

public record ImageResponse(String imagePath) {

    public static List<ImageResponse> toResponse(Place place){
        List<ImageResponse> responses = new ArrayList<>();
        for(Image image : place.getImages()){
            responses.add(new ImageResponse(image.getImagePath()));
        }
        return responses;
    }
}
