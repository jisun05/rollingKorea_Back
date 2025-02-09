package history.traveler.rollingkorea.place.controller.request;
import jakarta.annotation.Nullable;

public record ImageRequest(


        @Nullable String imagePath,
        @Nullable Long placeId

) {
}
