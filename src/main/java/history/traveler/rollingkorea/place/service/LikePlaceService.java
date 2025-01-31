package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.LikePlaceAddRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikePlaceService {

    //add LikePlace
    void addPlace(LikePlaceAddRequest likePlaceAddRequest);


    //search likeplace
    Page<LikePlaceResponse> findLikePlaceUser(Pageable pageable);

   //delete
   void deleteLikePlace(Long likePlaceId);


}
