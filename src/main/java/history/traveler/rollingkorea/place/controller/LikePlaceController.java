package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like-places") // ✅ 별도의 경로 지정
public class LikePlaceController {

    private final LikePlaceService likePlaceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Manage liked places", description = "Allows a user to like or unlike a place.")
    public void manageLikePlace(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @RequestBody @Valid LikePlaceManageRequest likePlaceManageRequest) {
        likePlaceService.manageLikePlace(userId, likePlaceManageRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find liked places by user ID", description = "Fetches the liked places for a specific user.")
    public Page<LikePlaceResponse> findAllByUser(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return likePlaceService.findAllByUser(userId, pageable);
    }
}
