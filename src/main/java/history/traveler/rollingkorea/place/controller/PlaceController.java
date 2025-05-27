package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceUpdateResponse;
import history.traveler.rollingkorea.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Operation(summary = "Retrieve places by region", description = "Fetches all places in a given region with pagination support.")
    @GetMapping(path = "/api/places", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaceResponse> placeFindByRegion(
            @RequestParam String region,
            @PageableDefault(sort = "placeId", direction = Sort.Direction.ASC) Pageable pageable) { // ASC로 수정!
        log.info("Response Status: 200 for region: {}", region);
        return placeService.findByRegion(region, pageable);
    }


    @Operation(summary = "Find Place by place ID", description = "Retrieves details of a specific place by its ID.")
    @GetMapping(path = "/api/places/{placeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceResponse> getPlaceById(@PathVariable Long placeId) {
        log.info("Request received to get place with ID: {}", placeId);
        PlaceResponse response = placeService.findPlaceById(placeId);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new place", description = "Registers a new place (Admin only).")
    @PostMapping(value = "/admin/places", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceCreateResponse addPlace(
            @RequestPart("placeData") PlaceCreateRequest placeCreateRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        log.info("Creating new place: {}", placeCreateRequest.placeName());
        return placeService.placeCreate(placeCreateRequest, imageFile);
    }

    @Operation(summary = "Update a place", description = "Updates the details of an existing place (Admin only).")
    @PutMapping(value = "/admin/places/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PlaceUpdateResponse updatePlace(
            @PathVariable Long id,
            @RequestPart("placeEditRequest") PlaceEditRequest placeEditRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        log.info("Updating place with id: {}", id);
        return placeService.placeUpdate(id, placeEditRequest, imageFile);
    }

    @Operation(summary = "Delete a place", description = "Deletes a place by place ID (Admin only).")
    @DeleteMapping("/admin/places/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        log.info("Deleting place with id: {}", id);
        boolean isDeleted = placeService.placeDelete(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

