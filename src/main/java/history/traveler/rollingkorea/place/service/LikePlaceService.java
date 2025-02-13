package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikePlaceService {

    //add LikePlace
    void manageLikePlace(LikePlaceManageRequest likePlaceManageRequest);

    Page<LikePlaceResponse> findAllByUser(Pageable pageable);

}
