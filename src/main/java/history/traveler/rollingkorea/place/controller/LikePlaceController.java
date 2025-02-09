package history.traveler.rollingkorea.place.controller;
import history.traveler.rollingkorea.place.controller.request.LikePlaceAddRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import history.traveler.rollingkorea.place.service.LikePlaceService;
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
    public void addPlace(@RequestBody @Valid LikePlaceAddRequest likePlaceAddRequest){
        likePlaceService.addPlace(likePlaceAddRequest);
    }

    //search likePlace
    public Page<LikePlaceResponse> findLikePlace(@PageableDefault(sort ="likePlace_id", direction = Sort.Direction.DESC)Pageable pageable){
        return likePlaceService.findLikePlaceUser(pageable);
    }

    //delete
    @DeleteMapping("/likePlace/{likePlaceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('User')")
    public void deleteLikePlace(@PathVariable("likePlaceId") Long likePlaceId){
        likePlaceService.deleteLikePlace(likePlaceId);
    }

}
