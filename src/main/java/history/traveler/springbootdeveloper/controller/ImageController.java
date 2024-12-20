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
        private final String imagePath = "D:/Traveler/imgKorea/";

        @GetMapping("/api/images/{imageName}")
        public ResponseEntity<FileSystemResource> getImage(@PathVariable String imageName) {
            File file = new File(imagePath + imageName);

            if (file.exists()) {
                FileSystemResource resource = new FileSystemResource(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "image/jpeg");
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if no file return 404
            }
        }
    }

