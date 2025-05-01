package history.traveler.rollingkorea.heritage.controller;

import history.traveler.rollingkorea.heritage.service.HeritageBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batch")
public class HeritageBatchTestController {

    private final HeritageBatchService heritageBatchService;

    public HeritageBatchTestController(HeritageBatchService heritageBatchService) {
        this.heritageBatchService = heritageBatchService;
    }

    @PostMapping("/heritages")
    public ResponseEntity<String> runBatchNow() {
        heritageBatchService.fetchAndSaveHeritagesWeekly();
        return ResponseEntity.ok("✅ 배치 실행 완료");
    }
}
