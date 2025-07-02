package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceUpdateResponse;
import history.traveler.rollingkorea.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @Operation(
            summary = "Retrieve places by area code",
            description = "Fetches paginated places for the given heritage areaCode."
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PlaceResponse>> findByAreaCode(
            @RequestParam("areaCode") Integer areaCode,
            @PageableDefault(sort = "contentId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (areaCode == null) {
            log.warn("areaCode parameter is missing");
            return ResponseEntity.badRequest().build();
        }
        log.info("Fetching places for areaCode={}", areaCode);
        Page<PlaceResponse> page = placeService.findByAreaCode(areaCode, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Find place by content ID",
            description = "Retrieves details of a single place by its contentId."
    )
    @GetMapping(path = "/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceResponse> getPlaceByContentId(@PathVariable Long contentId) {
        log.info("Fetching place with contentId={}", contentId);
        PlaceResponse response = placeService.findPlaceByContentId(contentId);
        return response != null
                ? ResponseEntity.ok(response)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Import a place",
            description = "Imports a place from heritage API (admin only)."
    )
    @PostMapping(
            path = "/admin",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceCreateResponse importPlace(
            @RequestPart("placeData") PlaceCreateRequest request,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        log.info("Admin import place contentId={}", request.contentId());
        return placeService.placeCreate(request, imageFile);
    }

    @Operation(
            summary = "Update a place",
            description = "Updates an existing place (admin only)."
    )
    @PutMapping(
            path = "/admin/{contentId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlaceUpdateResponse> updatePlace(
            @PathVariable Long contentId,
            @RequestPart("placeEditRequest") PlaceEditRequest request,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        log.info("Admin update place contentId={}", contentId);
        PlaceUpdateResponse updated = placeService.placeUpdate(contentId, request, imageFile);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete a place",
            description = "Deletes a place by contentId (admin only)."
    )
    @DeleteMapping(path = "/admin/{contentId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long contentId) {
        log.info("Admin delete place contentId={}", contentId);
        boolean deleted = placeService.placeDelete(contentId);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
