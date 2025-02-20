package history.traveler.rollingkorea.place.controller.request;

import javax.validation.constraints.NotNull;

public record PlaceEditRequest (

        @NotNull(message = "The placeName is a required field.")
        String placeName,
        @NotNull
        String region,
        @NotNull
        double latitude,
        @NotNull
        double longitude,
        @NotNull
        String placeDescription
)
{}
