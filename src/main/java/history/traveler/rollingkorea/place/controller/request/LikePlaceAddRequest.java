package history.traveler.rollingkorea.place.controller.request;
import history.traveler.rollingkorea.place.domain.Place;
import javax.validation.constraints.NotNull;

public record LikePlaceAddRequest (

    @NotNull
    Place place,

    @NotNull
    Long likePlaceId

){




}
