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

    @PostMapping("/sync")
    public ResponseEntity<Void> syncAll() {
        try {
            heritageService.fetchAndSaveHeritagesFromTourAPI();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .build();
        }
    }

    @GetMapping
    public List<Heritage> listAll() {
        return heritageService.getAllFromDatabase();
    }
}
