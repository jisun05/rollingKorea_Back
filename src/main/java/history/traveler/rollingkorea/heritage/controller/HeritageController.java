package history.traveler.rollingkorea.heritage.controller;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heritages")
public class HeritageController {

    private final HeritageService heritageService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchFromTourApi() throws Exception {
        heritageService.fetchAndSaveHeritagesFromTourAPI();
        return ResponseEntity.ok("✅ TourAPI로부터 유적지 정보 저장 완료");
    }

    @GetMapping
    public ResponseEntity<List<Heritage>> getAll() {
        return ResponseEntity.ok(heritageService.getAllFromDatabase());
    }

    @PostMapping("/sync")
    public ResponseEntity<String> fetchAndSave() throws Exception {
        heritageService.fetchAndSaveHeritagesFromTourAPI();  // TourAPI에서 받아오기
        return ResponseEntity.ok("✅ 관광지 정보 저장 완료");
    }



}
