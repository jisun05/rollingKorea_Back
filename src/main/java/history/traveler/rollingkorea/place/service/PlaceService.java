package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getPlacesByRegion(String region) {
        List<Place> places = placeRepository.findByRegion(region);

        return places.stream().peek(place -> {
            // HTTP URL 형식으로 이미지 경로 설정
            place.setImagePath("http://localhost:8080/api/images/" + place.getImagePath());
        }).collect(Collectors.toList());
    }
}