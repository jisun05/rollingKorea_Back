package history.traveler.rollingkorea.place.controller.response;

import history.traveler.rollingkorea.place.domain.LikePlace;

public record LikePlaceResponse(

        Long userId,
        Long placeId



) {

    public LikePlaceResponse(LikePlace likePlace) {

        this(likePlace.getUser().getUserId(), likePlace.getPlaceId());


    }
}
