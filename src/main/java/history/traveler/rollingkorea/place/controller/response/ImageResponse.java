package history.traveler.rollingkorea.place.controller.response;

import java.util.Base64;

public record ImageResponse(
        Long imageId,
        String imageUrl
) {}