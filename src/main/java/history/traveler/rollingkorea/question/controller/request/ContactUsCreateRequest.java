package history.traveler.rollingkorea.question.controller.request;

import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public record ContactUsCreateRequest(
        String content,
        MultipartFile file // MultipartFile로 변경
) {
    // 파일의 데이터를 byte[]로 반환
    public Blob fileData() throws IOException {
        if (file != null && !file.isEmpty()) {
            try {
                return new SerialBlob(file.getBytes());
            } catch (SQLException e) {
                throw new IOException("파일 데이터를 Blob으로 변환하는데 실패했습니다.", e);
            }
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
