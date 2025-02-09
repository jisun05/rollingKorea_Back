package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.ImageRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceResponse> findByRegion(String region, Pageable pageable) {
        // region으로 검색 후 모든 place 찾기
        Page<Place> places = placeRepository.findByRegion(pageable, region);

        // Place 객체들을 PlaceResponse 객체로 변환
        return places.map(place -> PlaceResponse.from(place, imageRepository));  // from() 메서드 사용
    }

    @Override
    @Transactional
    public void update(Long id, PlaceEditRequest placeEditRequest) throws IOException {

        Place place = placeRepository.findByPlaceId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // Place 정보 업데이트
        place.update(placeEditRequest);

        // 이미지 업데이트 로직
        if (placeEditRequest.imageRequests() != null && !placeEditRequest.imageRequests().isEmpty()) {
            List<String> newImagePaths = placeEditRequest.imageRequests().stream()
                    .map(ImageRequest::imagePath)  // ImageRequest에서 imagePath 추출
                    .toList();

            // 기존 이미지 삭제 (이미지 테이블에서 해당 placeId에 해당하는 이미지들 삭제)
            imageRepository.deleteByPlace_PlaceId(id);  // PlaceId에 해당하는 이미지 모두 삭제

            // 새로운 이미지 추가
            for (String imagePath : newImagePaths) {
                Image image = Image.builder()
                        .imagePath(imagePath)
                        .place(place)  // 해당 Place와 연결
                        .build();
                imageRepository.save(image);  // 새로운 이미지 저장
            }
        }
    }
    @Override
    @Transactional
    public boolean placeDelete(Long id) {
        if (placeRepository.existsById(id)) {
            imageRepository.deleteByPlace_PlaceId(id);
            placeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Place placeCreate(PlaceCreateRequest placeCreateRequest) {

        LocalDateTime now = LocalDateTime.now();

        Place newPlace = Place.builder()
                .placeName(placeCreateRequest.placeName())
                .placeDescription(placeCreateRequest.placeDescription())
                .region(placeCreateRequest.region())
                .longitude(placeCreateRequest.longitude())
                .latitude(placeCreateRequest.latitude())
                .website(placeCreateRequest.website())
                .createdAt(now) // 생성일시 추가
                .updatedAt(now) // 수정일시 추가
                .build();

        // 2. Image가 있는 경우 place 설정 후 저장
        if (placeCreateRequest.imageRequest() != null && placeCreateRequest.imageRequest().imagePath() != null) {
            Image newImage = Image.builder()
                    .imagePath(placeCreateRequest.imageRequest().imagePath()) // 이미지 경로 설정
                    .place(newPlace)  //  Place 객체 설정 (placeId 자동 세팅됨)
                    .build();
            imageRepository.save(newImage); // Image 저장
        }
        return placeRepository.save(newPlace);
    }

    // Place 객체를 PlaceResponse 객체로 변환하는 메서드
    private PlaceResponse convertToResponse(Place place) {
        return PlaceResponse.from(place, imageRepository);
    }
}
