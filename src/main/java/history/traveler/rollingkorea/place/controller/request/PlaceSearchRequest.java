package history.traveler.rollingkorea.place.controller.request;

import javax.validation.constraints.NotNull;


public record PlaceSearchRequest(


        @NotNull
        String region


) {
}
