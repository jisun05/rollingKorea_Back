package history.traveler.rollingkorea.heritage.service.impl;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.dto.response.HeritageResponse;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private static final Logger log = LoggerFactory.getLogger(HeritageServiceImpl.class);

    private static final int BATCH_SIZE = 100;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final WebClient webClient;
    private final HeritageRepository heritageRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final EntityManager entityManager;
    private final ThreadPoolTaskExecutor imageDownloadExecutor;

    @Value("${heritage.api.key}")
    private String serviceKey;

    @Override
    @Transactional
    public void fetchAndSaveHeritagesFromTourAPI() throws Exception {
        int pageNo = 1, pageSize = 100;

        List<Heritage> heritageBatch = new ArrayList<>();
        List<Place> placeBatch     = new ArrayList<>();
        List<Image> imageBatch     = new ArrayList<>();

        while (true) {
            HeritageRequest req = new HeritageRequest(
                    serviceKey, "ETC", "AppTest", "json", "C", 76, pageSize, pageNo
            );

            // 1) HeritageResponse 논블로킹 조회 (블록하여 페이징 제어)
            HeritageResponse resp = webClient.get()
                    .uri(req.toUri())
                    .retrieve()
                    .bodyToMono(HeritageResponse.class)
                    .block();
            var items = resp.wrapper().body().items().itemList();
            if (items.isEmpty()) break;

            for (var item : items) {
                // 2) Heritage, Place 객체 생성
                Heritage h = toEntity(item);
                heritageBatch.add(h);

                Place p = Place.fromHeritage(h);
                placeBatch.add(p);

                // 3) 이미지 URL 리스트
                List<String> urls = List.of(item.firstimage(), item.firstimage2()).stream()
                        .filter(u -> u != null && !u.isBlank())
                        .toList();

                // 4) 이미지 다운로드 태스크를 executor에 제출
                List<Future<Image>> futures = new ArrayList<>();
                for (String url : urls) {
                    futures.add(imageDownloadExecutor.submit(() -> {
                        byte[] data = safeFetchImage(url);
                        if (data != null && data.length > 0) {
                            return Image.builder()
                                    .place(p)
                                    .imageData(data)
                                    .build();
                        }
                        return null;
                    }));
                }
                // 5) 모든 태스크 결과를 모아서 imageBatch에 추가
                for (Future<Image> f : futures) {
                    Image img = f.get(60, TimeUnit.SECONDS); // 최대 60초 대기
                    if (img != null) {
                        imageBatch.add(img);
                    }
                }

                // 6) 배치 사이즈 도달 시 저장
                if (heritageBatch.size() >= BATCH_SIZE) {
                    flushAndClear(heritageBatch, placeBatch, imageBatch);
                }
            }

            if (pageNo * pageSize >= resp.wrapper().body().totalCount()) break;
            pageNo++;
        }

        // 남은 데이터 저장
        if (!heritageBatch.isEmpty()) {
            flushAndClear(heritageBatch, placeBatch, imageBatch);
        }
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }

    /** WebClient로 이미지 바이트를 안전하게 가져오기 */
    private byte[] safeFetchImage(String url) {
        return webClient.get()
                .uri(url)
                .exchangeToMono(this::handleImageResponse)
                .onErrorResume(ex -> {
                    log.warn("이미지 호출 중 예외 URL={} 메시지={}", url, ex.toString());
                    return Mono.empty();
                })
                .block();
    }

    private Mono<byte[]> handleImageResponse(ClientResponse response) {
        if (response.statusCode().is2xxSuccessful()) {
            return response.bodyToMono(byte[].class);
        } else {
            log.warn("이미지 응답 상태 비정상: {}", response.statusCode());
            return Mono.empty();
        }
    }

    /** 배치 저장 후 플러시 & 1차 캐시 클리어 */
    private void flushAndClear(
            List<Heritage> hBatch,
            List<Place>    pBatch,
            List<Image>    iBatch
    ) {
        heritageRepository.saveAll(hBatch);
        placeRepository.saveAll(pBatch);
        imageRepository.saveAll(iBatch);

        entityManager.flush();
        entityManager.clear();

        log.debug(">> Batch flush: saved {} Heritage, {} Place, {} Image",
                hBatch.size(), pBatch.size(), iBatch.size());

        hBatch.clear();
        pBatch.clear();
        iBatch.clear();
    }

    //====================================
    // 아래부터는 기존과 동일한 파싱 헬퍼 메서드
    //====================================

    private Heritage toEntity(HeritageResponse.HeritageItem item) {
        return Heritage.builder()
                .contentId(parseLongSafe(item.contentid()))
                .title(item.title())
                .addr1(item.addr1())
                .addr2(item.addr2())
                .areaCode(parseIntegerSafe(item.areacode()))
                .cat1(item.cat1())
                .cat2(item.cat2())
                .cat3(item.cat3())
                .contentTypeId(parseIntegerSafe(item.contenttypeid()))
                .createdTime(parseDateTimeSafe(item.createdtime()))
                .copyrightDivCd(item.cpyrhtDivCd())
                .mapX(parseDoubleSafe(item.mapx()))
                .mapY(parseDoubleSafe(item.mapy()))
                .mLevel(parseIntegerSafe(item.mlevel()))
                .modifiedTime(parseDateTimeSafe(item.modifiedtime()))
                .sigunguCode(parseIntegerSafe(item.sigungucode()))
                .tel(item.tel())
                .zipcode(item.zipcode())
                .lDongRegnCd(parseIntegerSafe(item.lDongRegnCd()))
                .lDongSignguCd(parseIntegerSafe(item.lDongSignguCd()))
                .lclsSystm1(item.lclsSystm1())
                .lclsSystm2(item.lclsSystm2())
                .lclsSystm3(item.lclsSystm3())
                .build();
    }

    private LocalDateTime parseDateTimeSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDateTime.parse(s, DTF);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    private Long parseLongSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Long.valueOf(s);
        } catch (NumberFormatException e) { return null; }
    }
    private Integer parseIntegerSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.valueOf(s);
        } catch (NumberFormatException e) { return null; }
    }
    private Double parseDoubleSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Double.valueOf(s);
        } catch (NumberFormatException e) { return null; }
    }
}
