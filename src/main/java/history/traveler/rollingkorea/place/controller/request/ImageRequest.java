package history.traveler.rollingkorea.place.controller.request;

import javax.validation.constraints.NotNull;

public record ImageRequest(


        @NotNull(message = "파일경로를 입력하세요.") String imagePath,
        @NotNull Long placeId

) {
}
