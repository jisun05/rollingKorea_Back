package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

public record LikePlaceManageRequest(

    @NotNull
    long placeId,
    long userId

){




}
