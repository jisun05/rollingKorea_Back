package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.ImageRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;

import java.io.IOException;

@RestController
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Operation(summary = "Retrieve places by region", description = "Fetches all places in a given region with pagination support.")
    @GetMapping(path = "/api/places",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaceResponse> placeFindByRegion(@RequestParam String region, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Response Status: 200 for region: {}", region);
        return placeService.findByRegion(region, pageable);
    }

    @Operation(summary = "Find Place by place ID", description = "Retrieves details of a specific place by its ID.")
    @GetMapping(path = "/api/places/{placeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlaceResponse> getPlaceById(@PathVariable Long placeId) {
        logger.info("Request received to get place with ID: {}", placeId);
        PlaceResponse placeResponse = placeService.findPlaceById(placeId);

        if (placeResponse != null) {
            return new ResponseEntity<>(placeResponse, HttpStatus.OK);
        } else {
            logger.error("Place not found for ID: {}", placeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new place", description = "Registers a new place (Admin only).")
    @PostMapping(value = "/admin/places", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceCreateResponse addPlace(
            @Parameter(
                    description = "Place data in JSON format",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlaceCreateRequest.class))
            )
            @RequestPart("placeData") PlaceCreateRequest placeCreateRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        return placeService.placeCreate(placeCreateRequest, imageFile);
    }


    @Operation(summary = "Update a place", description = "Updates the details of an existing place (Admin only).")
    @PutMapping("/admin/places/{id}") // 이후 url수정필요
    //@PreAuthorize("hasRole('ADMIN')") // ADMIN만 호출 가능
    public void updatePlace(@PathVariable Long id, @RequestBody PlaceEditRequest placeEditRequest, ImageRequest imageRequest) throws IOException {
        placeService.placeUpdate(id, placeEditRequest, imageRequest);
    }

    @Operation(summary = "Delete a place", description = "Deletes a place by place ID (Admin only).")
    @DeleteMapping("/admin/places/{id}")
    //@PreAuthorize("hasRole('ADMIN')") // ADMIN만 호출 가능
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        boolean isDeleted = placeService.placeDelete(id);
        if (isDeleted) {
            logger.info("Place deleted with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Place not found for id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
