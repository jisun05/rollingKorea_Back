//package history.traveler.rollingkorea.heritage.controller;
//
//import history.traveler.rollingkorea.heritage.dto.response.HeritageResponse;
//import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
//import history.traveler.rollingkorea.heritage.domain.Heritage;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/heritages")
//public class HeritageController {
//
//    private final HeritageRepository heritageRepository;
//
//    public HeritageController(HeritageRepository heritageRepository) {
//        this.heritageRepository = heritageRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<HeritageResponse>> getAllHeritages() {
//        List<Heritage> heritageList = heritageRepository.findAll();
//
//        List<HeritageResponse> responseList = heritageList.stream()
//                .map(HeritageResponse::from)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(responseList);
//    }
//}
