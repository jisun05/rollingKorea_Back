package history.traveler.springbootdeveloper.service;

import history.traveler.springbootdeveloper.domain.Place;
import history.traveler.springbootdeveloper.repository.PlaceRepository;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getPlacesByRegion(String region) {
        // region에 따른 장소 데이터를 데이터베이스에서 조회
        return placeRepository.findByRegion(region);
    }
}
