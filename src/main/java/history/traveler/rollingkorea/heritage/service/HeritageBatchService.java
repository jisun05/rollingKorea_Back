package history.traveler.rollingkorea.heritage.service;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeritageBatchService {

    private final HeritageService heritageService;
    private final HeritageRepository heritageRepository;

    public HeritageBatchService(HeritageService heritageService, HeritageRepository heritageRepository) {
        this.heritageService = heritageService;
        this.heritageRepository = heritageRepository;
    }

    @Scheduled(cron = "0 0 1 ? * MON") // 매주 월요일 1시
    public void fetchAndSaveHeritagesWeekly() {
        try {
            List<HeritageRequest> dtoList = heritageService.getHeritages();

            List<Heritage> entityList = dtoList.stream()
                    .map(HeritageRequest::toEntity)
                    .collect(Collectors.toList());

            heritageRepository.saveAll(entityList);

            System.out.println("✅ 국가유산 배치 완료: " + entityList.size() + "건 저장됨");
        } catch (Exception e) {
            System.err.println("❌ 국가유산 배치 중 오류 발생: " + e.getMessage());
        }
    }
}
