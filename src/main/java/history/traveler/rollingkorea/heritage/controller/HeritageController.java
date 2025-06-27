package history.traveler.rollingkorea.heritage.controller;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritages")
@RequiredArgsConstructor
public class HeritageController {

    private final HeritageService heritageService;

    /**
     * TourAPI에서 데이터를 페치해서 DB에 저장한 뒤,
     * 저장된 Heritage 엔티티 전체를 그대로 반환
     */
    @PostMapping("/sync")
    public ResponseEntity<List<Heritage>> syncAll() throws Exception {
        // A) DB에 저장만 하는 호출
        heritageService.fetchAndSaveHeritagesFromTourAPI();

        // B) 저장된 전체 Heritage 리스트를 꺼내서 그대로 반환
        List<Heritage> entities = heritageService.getAllFromDatabase();
        return ResponseEntity.ok(entities);
    }

}
