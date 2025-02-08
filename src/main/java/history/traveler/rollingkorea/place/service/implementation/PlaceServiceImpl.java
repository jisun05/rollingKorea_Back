package history.traveler.rollingkorea.place.service.implementation;

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
    public Page<PlaceResponse> placeFindAll(Pageable pageable) {
        // 모든 유적지를 페이지네이션하여 반환
        return placeRepository.findAll(pageable).map(this::convertToResponse);
    }

    @Override
    public List<Place> findByRegion(String region) {

        return placeRepository.findByRegion(region);
    }

    @Override
    @Transactional
    public Place update(Long id, PlaceEditRequest placeEditRequest) throws IOException {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + id));

        // Builder 패턴을 사용하여 수정
        Place updatedPlace = Place.builder()
                .placeId(place.getPlaceId()) // 기존 ID 유지  왜 이렇게 하는지? 세팅안하면 그대로 이지 않나?
                .placeName(placeEditRequest.placeName())
                .placeDescription(placeEditRequest.placeDescription())
                .region(placeEditRequest.region())
                .createdAt(place.getCreatedAt()) // 기존 생성일시 유지
                .updatedAt(LocalDateTime.now()) // 수정일시 업데이트
                .build();

        return placeRepository.save(updatedPlace);

    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (placeRepository.existsById(id)) {
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
                .createdAt(now) // 생성일시 추가
                .updatedAt(now) // 수정일시 추가
                .build();

        // 이미지를 저장할 때 imagePath가 null이 아닐 경우에만 저장
        if (placeCreateRequest.imageRequest() != null &&
                placeCreateRequest.imageRequest().imagePath() != null) {

            Image newImage = Image.builder()
                    .imagePath(placeCreateRequest.imageRequest().imagePath())
                    .build();

            imageRepository.save(newImage);
        }

        return placeRepository.save(newPlace);
    }

    @Override
    public List<Image> findImagesByPlaceId(Long placeId) {
        return imageRepository.findByPlace_PlaceId(placeId);
    }


    // Place 객체를 PlaceResponse 객체로 변환하는 메서드
    private PlaceResponse convertToResponse(Place place) {
        return PlaceResponse.from(place, imageRepository);
    }

}
