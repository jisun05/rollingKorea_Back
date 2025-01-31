package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;



@RestController
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private final String imagePath = "D:/Traveler/imgKorea/";
    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/place")
    public List<Place> getPlaces(@RequestParam String region) {
        return placeService.findByRegion(region);
    }

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
