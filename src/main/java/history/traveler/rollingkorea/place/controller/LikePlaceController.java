package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikePlaceController {

    private final LikePlaceService likePlaceService;

    //add place
    @PostMapping("/like-place")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void manageLikePlace(@RequestBody @Valid LikePlaceManageRequest likePlaceManageRequest){
        likePlaceService.manageLikePlace(likePlaceManageRequest);
    }

    //search likePlace
    @GetMapping("/api/like-place")
    @Operation(summary = "Find likePlaces by user ID", description = "Fetches the likePlaces for a specific user.")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public Page<LikePlaceResponse> findAllByUser
    (@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return likePlaceService.findAllByUser(pageable);
    }

}
