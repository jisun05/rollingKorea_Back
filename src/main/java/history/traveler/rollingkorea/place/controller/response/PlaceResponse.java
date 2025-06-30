package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PlaceResponse(
        Long contentId,
        String title,
        String addr1,
        String addr2,
        Double mapX,
        Double mapY,
        LocalDateTime importedAt,
        List<ImageResponse> imageList,
        boolean liked
) {

    /** Place → PlaceResponse 변환 */
    public static PlaceResponse from(Place place,
                                     ImageRepository imageRepository,
                                     boolean liked) {
        List<Image> images = imageRepository.findByPlace_ContentId(place.getContentId());
        return PlaceResponse.builder()
                .contentId(place.getContentId())
                .title(place.getTitle())
                .addr1(place.getAddr1())
                .addr2(place.getAddr2())
                .mapX(place.getMapX())
                .mapY(place.getMapY())
                .importedAt(place.getImportedAt())
                .imageList(images.stream()
                        .map(img -> new ImageResponse(
                                img.getImageId(),
                                "/api/places/" + place.getContentId() + "/images/" + img.getImageId()
                        ))
                        .collect(Collectors.toList()))
                .liked(liked)
                .build();
    }
}
