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
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Operation(
            summary = "Retrieve places by area code",
            description = "Fetches all places with the given area code (heritage 기준) and supports pagination."
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaceResponse> findByAreaCode(
            @RequestParam("areaCode") Integer areaCode,
            @PageableDefault(sort = "contentId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.info("Response Status: 200 for areaCode: {}", areaCode);
        return placeService.findByAreaCode(areaCode, pageable);
    }

    @Operation(
            summary = "Find place by content ID",
            description = "Retrieves details of a specific place by its heritage-derived contentId."
    )
    @GetMapping(path = "/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceResponse> getPlaceByContentId(@PathVariable Long contentId) {
        log.info("Request received to get place with contentId: {}", contentId);
        PlaceResponse response = placeService.findPlaceByContentId(contentId);
        return (response != null)
                ? ResponseEntity.ok(response)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Import a heritage place",
            description = "Imports data from external heritage API into Place (Admin only)."
    )
    @PostMapping(
            path = "/admin",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceCreateResponse importPlace(
            @RequestPart("placeData") PlaceCreateRequest placeCreateRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        log.info("Importing place with contentId: {}", placeCreateRequest.contentId());
        return placeService.placeCreate(placeCreateRequest, imageFile);
    }

    @Operation(
            summary = "Update a place",
            description = "Updates the mutable fields of an existing place (Admin only)."
    )
    @PutMapping(
            path = "/admin/{contentId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PlaceUpdateResponse updatePlace(
            @PathVariable Long contentId,
            @RequestPart("placeEditRequest") PlaceEditRequest placeEditRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        log.info("Updating place with contentId: {}", contentId);
        return placeService.placeUpdate(contentId, placeEditRequest, imageFile);
    }

    @Operation(
            summary = "Delete a place",
            description = "Deletes a place by contentId (Admin only)."
    )
    @DeleteMapping(path = "/admin/{contentId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long contentId) {
        log.info("Deleting place with contentId: {}", contentId);
        boolean deleted = placeService.placeDelete(contentId);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
