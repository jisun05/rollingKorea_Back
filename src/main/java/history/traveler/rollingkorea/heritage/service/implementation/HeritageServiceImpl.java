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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private static final Logger log = LoggerFactory.getLogger(HeritageServiceImpl.class);

    // 한 배치에 모아서 저장할 사이즈
    private static final int BATCH_SIZE = 50;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final RestTemplate restTemplate;
    private final HeritageRepository heritageRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final EntityManager entityManager;  // 배치 처리 후 flush/clear 용

    @Value("${heritage.api.key}")
    private String serviceKey;

    @Override
    @Transactional
    public void fetchAndSaveHeritagesFromTourAPI() throws Exception {
        int pageNo = 1, pageSize = 100;

        // 배치용 컬렉션
        List<Heritage> heritageBatch = new ArrayList<>();
        List<Place> placeBatch = new ArrayList<>();
        List<Image> imageBatch = new ArrayList<>();

        while (true) {
            HeritageRequest req = new HeritageRequest(
                    serviceKey, "ETC", "AppTest", "json", "C", 76, pageSize, pageNo
            );
            HeritageResponse resp = restTemplate.getForObject(
                    req.toUri(), HeritageResponse.class
            );
            var body = resp.wrapper().body();
            var items = body.items().itemList();
            if (items.isEmpty()) break;

            for (var item : items) {
                // 1) Heritage 엔티티 생성
                Heritage h = toEntity(item);
                heritageBatch.add(h);

                // 2) Place 엔티티 생성
                Place p = Place.fromHeritage(h);
                placeBatch.add(p);

                // 3) 이미지 URL 리스트
                List<String> urls = List.of(item.firstimage(), item.firstimage2()).stream()
                        .filter(u -> u != null && !u.isBlank())
                        .toList();

                // 4) 병렬로 이미지 다운로드
                urls.parallelStream().forEach(url -> {
                    byte[] data = safeFetch(url);
                    if (data != null && data.length > 0) {
                        synchronized (imageBatch) {
                            imageBatch.add(Image.builder()
                                    .place(p)
                                    .imageData(data)
                                    .build());
                        }
                    }
                });

                // 배치 크기만큼 모였으면 저장
                if (heritageBatch.size() >= BATCH_SIZE) {
                    flushAndClear(heritageBatch, placeBatch, imageBatch);
                }
            }

            // 다음 페이지로
            if (pageNo * pageSize >= body.totalCount()) break;
            pageNo++;
        }

        // 남은 배치 처리
        if (!heritageBatch.isEmpty()) {
            flushAndClear(heritageBatch, placeBatch, imageBatch);
        }
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }

    /** 안전하게 HTTP GET 으로 바이트[] 반환 (404, content-type 오류 등 모두 잡아서 null 리턴) */
    private byte[] safeFetch(String url) {
        try {
            return restTemplate.getForObject(url, byte[].class);
        } catch (UnknownContentTypeException ex) {
            log.warn("URL='{}' 응답이 이미지가 아님: {}", url, ex.getMessage());
        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("URL='{}' 이미지 없음(404): {}", url, ex.getStatusCode());
        } catch (HttpClientErrorException ex) {
            log.warn("URL='{}' HTTP 오류: {} {}", url, ex.getStatusCode(), ex.getStatusText());
        } catch (Exception ex) {
            log.error("URL='{}' 처리 중 예외 발생", url, ex);
        }
        return null;
    }

    /** 모아둔 배치를 저장하고 1차 캐시 비우기 */
    private void flushAndClear(
            List<Heritage> hBatch,
            List<Place> pBatch,
            List<Image> iBatch
    ) {
        heritageRepository.saveAll(hBatch);
        placeRepository.saveAll(pBatch);
        imageRepository.saveAll(iBatch);

        entityManager.flush();
        entityManager.clear();

        hBatch.clear();
        pBatch.clear();
        iBatch.clear();

        log.debug(">> Batch flush: saved {} Heritage, {} Place, {} Image",
                BATCH_SIZE, BATCH_SIZE, iBatch.size());
    }

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
