package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.LikePlace;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record LikePlaceResponse(
        Long likePlaceId,
        Long contentId,
        String title,
        String addr1,
        String addr2,
        Double mapX,
        Double mapY,
        LocalDateTime importedAt,
        java.util.List<ImageResponse> images
) {
    public LikePlaceResponse(LikePlace likePlace) {
        this(
                likePlace.getLikePlaceId(),
                likePlace.getPlace().getContentId(),
                likePlace.getPlace().getTitle(),
                likePlace.getPlace().getAddr1(),
                likePlace.getPlace().getAddr2(),
                likePlace.getPlace().getMapX(),
                likePlace.getPlace().getMapY(),
                likePlace.getPlace().getImportedAt(),
                likePlace.getPlace().getImages().stream()
                        .map(img -> new ImageResponse(
                                img.getImageId(),
                                "/api/places/" +
                                        likePlace.getPlace().getContentId() +
                                        "/images/" + img.getImageId()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
