package history.traveler.rollingkorea.place.service;

import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;


// 이 클래스가 비즈니스 로직을 처리함을 명시
public interface PlaceService {


    //create place, image o
    void placeCreate(PlaceCreateRequest placeCreateRequest, List<MultipartFile> imagePath) throws IOException;

    //search whole place
    Page<PlaceResponse> placeFindAll(Pageable pageable);


    List<Place> findByRegion(String region);
}