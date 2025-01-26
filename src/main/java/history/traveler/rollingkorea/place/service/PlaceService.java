package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service // 이 클래스가 비즈니스 로직을 처리함을 명시
public class PlaceService {
    // PlaceRepository를 주입받기 위한 필드 선언
    private final PlaceRepository placeRepository;

    // 생성자: PlaceRepository를 주입받아 placeRepository 필드를 초기화
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getPlacesByRegion(String region) {
        // 지역에 해당하는 장소 목록을 PlaceRepository를 통해 조회
        List<Place> places = placeRepository.findByRegion(region);
        // 각 장소의 이미지 경로를 HTTP URL 형식으로 설정하고, 수정된 리스트를 반환
        return places.stream().map(place -> {
            // HTTP URL 형식으로 이미지 경로 설정
            place.setImagePath("http://localhost:8080/api/images/" + place.getImagePath());
            return place; // 수정된 장소를 반환
        }).toList(); // 수정된 장소 리스트를 수집하여 반환
    }



}