package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.LikePlace;

import java.util.List;
import java.util.stream.Collectors;

public record LikePlaceResponse(


        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        String countLike,
        List<ImageResponse> imageList


) {
    public LikePlaceResponse(LikePlace likePlace) {
        this(
                likePlace.getLikePlaceId(),
                likePlace.getPlace().getPlaceName(),
                likePlace.getPlace().getRegion(),
                likePlace.getPlace().getPlaceDescription(),
                likePlace.getPlace().getLatitude(),
                likePlace.getPlace().getLongitude(),
                likePlace.getPlace().getCountLike(),
                convertImagesToImageResponse(likePlace)
        );
    }



    private static List<ImageResponse> convertImagesToImageResponse(LikePlace likePlace) {
        // 이미지는 LikePlace와 연관된 이미지가 있는 경우 가져오는 로직을 작성합니다.
        // 예를 들어, 이미지를 관리하는 엔티티가 있다면, 그 엔티티에서 List<ImageResponse>를 반환하는 방식입니다.
        return likePlace.getPlace().getImages().stream()  // Place의 이미지 리스트를 가져옴
                .map(image -> new ImageResponse(image.getImagePath()))  // Image 객체를 ImageResponse로 변환
                .collect(Collectors.toList());
    }

}