package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.ImageRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void placeUpdate(Long id, PlaceEditRequest placeEditRequest, ImageRequest imageRequest) {
        Place place = placeRepository.findByPlaceId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // Place 정보 업데이트
        place.update(placeEditRequest);

        // 이미지 업데이트 로직: imageRequest가 null이 아니고, 이미지 파일이 존재하는 경우 처리
        if (imageRequest != null && imageRequest.imageData() != null && !imageRequest.imageData().isEmpty()) {
            // 기존 이미지 삭제 (해당 Place에 연결된 모든 이미지 삭제)
            imageRepository.deleteByPlace_PlaceId(id);

            try {
                byte[] newImageData = imageRequest.imageData().getBytes();

                Image image = Image.builder()
                        .imageData(newImageData)
                        .place(place)
                        .build();
                imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("이미지 파일 처리 중 오류가 발생했습니다.", e);
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
    public PlaceCreateResponse placeCreate(PlaceCreateRequest placeCreateRequest, MultipartFile imageFile) {
        // 같은 이름의 장소가 있으면 예외 처리
        if (placeRepository.findByPlaceName(placeCreateRequest.placeName()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_PLACE);
        }

        // 같은 좌표(latitude, longitude)의 장소가 있으면 예외 처리
        if (placeRepository.findByLatitudeAndLongitude(placeCreateRequest.latitude(), placeCreateRequest.longitude()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_LOCATION);
        }

        // 유적지(Place) 저장
        Place place = Place.create(placeCreateRequest);
        placeRepository.save(place);

        // 이미지가 첨부된 경우, 이미지 데이터를 추출하여 Image 엔티티로 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                byte[] imageData = imageFile.getBytes();
                Image image = Image.builder()
                        .imageData(imageData)
                        .place(place)
                        .build();
                imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("이미지 파일 처리 중 오류가 발생했습니다.", e);
            }
        }

        // 최종적으로 생성된 Place 엔티티를 기반으로 응답 객체 반환
        return PlaceCreateResponse.from(place);
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
