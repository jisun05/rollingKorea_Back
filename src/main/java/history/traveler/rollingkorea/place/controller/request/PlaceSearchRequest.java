package history.traveler.rollingkorea.place.controller.request;

import jakarta.validation.constraints.NotNull;

public record PlaceSearchRequest(

        @NotNull
        String region

) {
}
