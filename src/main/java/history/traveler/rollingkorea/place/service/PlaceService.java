package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface PlaceService {

    // 모든 유적지 검색
    Page<PlaceResponse> placeFindAll(Pageable pageable);

    // 지역별 유적지 검색
    List<Place> findByRegion(String region);

    // 관리자 유적지 수정
    Place update(Long id, PlaceEditRequest placeEditRequest) throws IOException;

    // 관리자 유적지 삭제
    boolean delete(Long id);

    //관리자 유적지 생성
    Place placeCreate(PlaceCreateRequest placeCreateRequest);
}
