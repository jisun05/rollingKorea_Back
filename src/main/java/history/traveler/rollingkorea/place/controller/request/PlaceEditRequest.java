package history.traveler.rollingkorea.place.controller.request;

import jakarta.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

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
        String placeDescription,
        @Nullable
        List<ImageRequest> imageRequests

)
{}
