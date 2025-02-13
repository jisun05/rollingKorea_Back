package history.traveler.rollingkorea.place.controller;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/likePlace")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void manageLikePlace(@RequestBody @Valid LikePlaceManageRequest likePlaceManageRequest){
        likePlaceService.manageLikePlace(likePlaceManageRequest);
    }

//    //search likePlace
//    @GetMapping("/api/likePlace/{userId}")
//    @Operation(summary = "Find likePlaces by user ID", description = "Fetches the likePlaces for a specific user.")
//    @ResponseStatus(HttpStatus.OK)
//    public Page<PlaceResponse> findAllByUser
//    (@Parameter(description = "The unique identifier of the user", required = true) @PathVariable User user,
//     @PageableDefault(sort = "like_place_id", direction = Sort.Direction.DESC) Pageable pageable){
//        return likePlaceService.findAllByUser(user ,pageable);
//    }

}
