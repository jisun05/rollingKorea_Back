package history.traveler.rollingkorea.question.controller.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record ContactUsCreateRequest(
        String content,
        MultipartFile file // MultipartFile로 변경
) {
    // 파일의 데이터를 byte[]로 반환
    public byte[] fileData() throws IOException {
        if (file != null && !file.isEmpty()) {
            return file.getBytes();
        }
        return null; // 파일이 없으면 null 반환
    }

    // 파일의 이름을 반환
    public String fileName() {
        if (file != null && !file.isEmpty()) {
            return file.getOriginalFilename();
        }
        return null; // 파일이 없으면 null 반환
    }
}
