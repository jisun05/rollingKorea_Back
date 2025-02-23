package history.traveler.rollingkorea.place.controller.request;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(
        @Nullable MultipartFile imageData
) {
    // DB에 저장 가능한 최대 파일 크기 (예: 2MB)
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public ImageRequest {
        if (imageData != null && imageData.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기가 DB에 저장 가능한 크기를 초과합니다. (최대 " + MAX_FILE_SIZE + " 바이트)");
        }
    }
}
