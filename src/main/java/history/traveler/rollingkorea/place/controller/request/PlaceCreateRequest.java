package history.traveler.rollingkorea.place.controller.request;

import jakarta.annotation.Nullable;

import javax.validation.constraints.NotNull;

public record PlaceCreateRequest(

        @NotNull(message = "The placeName is a required field.")
        String placeName,

        @NotNull
        String region,

        @NotNull
        double latitude,
        @NotNull
        double longitude,
        @Nullable
        String website,
        @NotNull
        String placeDescription
) {
}
