package history.traveler.springbootdeveloper.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

    @RestController
    public class ImageController {
        private final String imagePath = "C:/Traveler/imgKorea/"; // 이미지가 저장된 경로

        @GetMapping("/api/images/{imageName}") // 이미지 요청을 처리할 엔드포인트
        public ResponseEntity<FileSystemResource> getImage(@PathVariable String imageName) {
            File file = new File(imagePath + imageName); // 요청된 이미지 파일 생성

            if (file.exists()) {
                FileSystemResource resource = new FileSystemResource(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "image/jpeg"); // 이미지 타입 설정
                return new ResponseEntity<>(resource, headers, HttpStatus.OK); // 이미지 반환
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if no file return 404
            }
        }
    }

