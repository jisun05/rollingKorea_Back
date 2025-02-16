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
    public void placeUpdate(Long id, PlaceEditRequest placeEditRequest){

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
    public void placeCreate(PlaceCreateRequest placeCreateRequest) {

        // 같은 이름의 상품이 있으면 예외처리
        if (placeRepository.findByPlaceName(placeCreateRequest.placeName()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_PLACE);
        }

        // 같은 좌표(latitude, longitude)의 장소가 있으면 예외 처리
        if (placeRepository.findByLatitudeAndLongitude(placeCreateRequest.latitude(), placeCreateRequest.longitude()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_LOCATION);
        }

        //유적지 저장
        Place place = Place.create(placeCreateRequest);
        placeRepository.save(place);

        // 2. Image가 있는 경우 place 설정 후 저장
        if (placeCreateRequest.imageRequests() != null) {
            List<String> newImagePaths = placeCreateRequest.imageRequests().stream()
                    .map(ImageRequest::imagePath)  // ImageRequest에서 imagePath 추출
                    .toList();

            for (String imagePath : newImagePaths) {
                Image image = Image.builder()
                        .imagePath(imagePath)
                        .place(place)  // 해당 Place와 연결
                        .build();
                imageRepository.save(image);  // 새로운 이미지 저장
            }
        }
         placeRepository.save(place);
    }

    // New method to find a place by its ID
    @Override
    @Transactional(readOnly = true)
    public PlaceResponse findPlaceById(Long placeId) {
        // Find the place by its ID
        Place place = placeRepository.findByPlaceId(placeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // Return the corresponding PlaceResponse
        return PlaceResponse.from(place, imageRepository);
    }

    // Place 객체를 PlaceResponse 객체로 변환하는 메서드
    private PlaceResponse convertToResponse(Place place) {
        return PlaceResponse.from(place, imageRepository);
    }
}
