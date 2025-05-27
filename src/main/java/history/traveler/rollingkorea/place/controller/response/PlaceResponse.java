package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PlaceResponse(

        Long placeId,
        String placeName,
        String region,
        String placeDescription,
        double latitude,
        double longitude,
        String countLike,
        List<ImageResponse> imageList,
        boolean liked

)
{



    // Place 객체를 PlaceResponse로 변환하는 정적 메서드
    public static PlaceResponse from(Place place, ImageRepository imageRepository, boolean liked) {
        List<Image> images = imageRepository.findByPlace_PlaceId(place.getPlaceId());  // placeId로 이미지 목록 조회
        return PlaceResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .region(place.getRegion())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .placeDescription(place.getPlaceDescription())
                .countLike(place.getCountLike())
                .imageList(toImageResponseList(images))  // 이미지 리스트 변환
                .liked(liked)
                .build();
    }
    // 이미지를 ImageResponse로 변환하는 메서드
    private static List<ImageResponse> toImageResponseList(List<Image> images) {
        return images.stream()  // 이미지 리스트를 스트림으로 변환
                .map(image -> new ImageResponse(image.getImageData()))  // Image 엔티티를 ImageResponse로 변환
                .collect(Collectors.toList());  // 리스트로 수집
    }


}