package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.ImageRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PlaceService {

    // 지역별 유적지 검색
    Page<PlaceResponse> findByRegion(String region, Pageable pageable);

    // 관리자 유적지 수정
    void placeUpdate(Long id, PlaceEditRequest placeEditRequest, ImageRequest imageRequest) throws IOException;

    // 관리자 유적지 삭제
    boolean placeDelete(Long id);

    //관리자 유적지 생성
    PlaceCreateResponse placeCreate(PlaceCreateRequest placeCreateRequest, MultipartFile imageFile);

    //유적지 단건 조회
    PlaceResponse findPlaceById(Long placeId);
}
