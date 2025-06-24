package history.traveler.rollingkorea.heritage.controller;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritages")
public class HeritageController {

    private final HeritageService heritageService;

    public HeritageController(HeritageService heritageService) {
        this.heritageService = heritageService;
    }

    // 유적지 전체 조회
    @GetMapping
    public ResponseEntity<List<Heritage>> getAllHeritages() {
        List<Heritage> heritages = heritageService.getAllFromDatabase();
        return ResponseEntity.ok(heritages);
    }
}
