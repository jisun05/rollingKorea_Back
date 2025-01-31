package history.traveler.rollingkorea.place.service.Implementation;


import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.service.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Override
    public void placeCreate(PlaceCreateRequest placeCreateRequest, List<MultipartFile> imagePath) throws IOException {

    }

    @Override
    public Page<PlaceResponse> placeFindAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Place> findByRegion(String region) {
        return List.of();
    }
}
