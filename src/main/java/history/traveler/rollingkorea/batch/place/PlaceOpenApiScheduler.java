package history.traveler.rollingkorea.batch.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlaceOpenApiScheduler {

    private final PlaceOpenApiService placeOpenApiService;

    @Scheduled(cron = "0 0 1 * * MON") // 매주 월요일 새벽 1시
    public void sync() {
        log.info("📌 Place API 동기화 시작");
        placeOpenApiService.fetchAndSave();
        log.info("✅ 동기화 완료");
    }
}

