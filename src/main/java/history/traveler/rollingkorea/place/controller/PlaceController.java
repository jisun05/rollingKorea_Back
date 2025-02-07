package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
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
import java.io.IOException;
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
        logger.info("Request Method: GET, Request URI: /api/images/{}", imageName);
        File file = new File(imagePath + imageName);

        if (file.exists()) {
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpg");
            logger.info("Response Status: 200 for image: {}", imageName);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            logger.error("File not found: {}", imageName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 유적지 등록 (관리자)
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/place")
    public ResponseEntity<Place> addPlace(@RequestBody PlaceCreateRequest placeCreateRequest) {
        Place createdPlace = placeService.placeCreate(placeCreateRequest);
        logger.info("Place created: {}", createdPlace);
        return new ResponseEntity<>(createdPlace, HttpStatus.CREATED);
    }

    // 유적지 수정 (관리자)
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/api/place/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody PlaceEditRequest placeEditRequest) throws IOException {
        Place updatedPlace = placeService.update(id, placeEditRequest);
        if (updatedPlace != null) {
            logger.info("Place updated: {}", updatedPlace);
            return new ResponseEntity<>(updatedPlace, HttpStatus.OK);
        } else {
            logger.error("Place not found for id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 유적지 삭제 (관리자)
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/api/place/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        boolean isDeleted = placeService.delete(id);
        if (isDeleted) {
            logger.info("Place deleted with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Place not found for id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
