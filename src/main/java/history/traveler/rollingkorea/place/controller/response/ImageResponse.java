package history.traveler.rollingkorea.place.controller.response;

import java.util.Base64;

public record ImageResponse(String imageData) {
    public ImageResponse(byte[] imageData) {
        this(Base64.getEncoder().encodeToString(imageData));  // Base64로 변환!
    }
}
