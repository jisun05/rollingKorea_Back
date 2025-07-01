package history.traveler.rollingkorea.heritage.controller;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
   // @PreAuthorize("hasRole('ADMIN')")    // → ADMIN 권한이 있을 때만 실행
    public ResponseEntity<List<Heritage>> syncAll() throws Exception {
        heritageService.fetchAndSaveHeritagesFromTourAPI();
        List<Heritage> entities = heritageService.getAllFromDatabase();
        return ResponseEntity.ok(entities);
    }

}
