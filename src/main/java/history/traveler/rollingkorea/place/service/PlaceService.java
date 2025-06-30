package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceUpdateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceService {

    /**
     * heritage의 areaCode로 유적지 목록 조회 (페이징, 좋아요 여부 포함)
     */
    Page<PlaceResponse> findByAreaCode(Integer areaCode, Pageable pageable);

    /**
     * 단일 유적지 조회 (contentId 기준, 좋아요 여부 포함)
     */
    PlaceResponse findPlaceByContentId(Long contentId);

    /**
     * heritage API 데이터를 Place로 import (Admin only)
     */
    PlaceCreateResponse placeCreate(PlaceCreateRequest placeCreateRequest, MultipartFile imageFile);

    /**
     * 기존 Place의 mutable 필드(title, addr1, addr2 등) 업데이트 (Admin only)
     */
    PlaceUpdateResponse placeUpdate(Long contentId, PlaceEditRequest placeEditRequest, MultipartFile imageFile);

    /**
     * contentId 기준으로 Place 삭제 (Admin only)
     */
    boolean placeDelete(Long contentId);
}
