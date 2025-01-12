package history.traveler.springbootdeveloper.place;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/place")
    public List<Place> getPlaces(@RequestParam String region) {
        return placeService.getPlacesByRegion(region);
    }
}
