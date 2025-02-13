package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;

public interface LikePlaceService {

    //add LikePlace
    void manageLikePlace(LikePlaceManageRequest likePlaceManageRequest);

    //Page<PlaceResponse> findAllByUser(User user, Pageable pageable);

}
