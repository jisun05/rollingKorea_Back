package history.traveler.springbootdeveloper.place;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ImageController {
    private final String imagePath = "D:/Traveler/imgKorea/";
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("/api/images/{imageName}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String imageName) {
        logger.info("Request Method: GET, Request URI: /api/images/{}", imageName); // 요청 로그 추가
        File file = new File(imagePath + imageName);

        if (file.exists()) {
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpg");
            logger.info("Response Status: 200 for image: {}", imageName); // 응답 로그 추가
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            logger.error("File not found: {}", imageName); // 파일이 없을 때 에러 로그
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if no file return 404
        }
    }
}

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("An error occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
