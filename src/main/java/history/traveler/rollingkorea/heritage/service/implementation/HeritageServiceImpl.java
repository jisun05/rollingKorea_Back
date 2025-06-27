package history.traveler.rollingkorea.heritage.service.impl;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.dto.response.HeritageResponse;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private final RestTemplate restTemplate;
    private final HeritageRepository heritageRepository;

    @Value("${heritage.api.key}")
    private String serviceKey;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    @Transactional
    public void  fetchAndSaveHeritagesFromTourAPI() throws Exception {
        int pageNo = 1, pageSize = 100;
        while (true) {
            HeritageRequest req = new HeritageRequest(
                    serviceKey, "ETC", "AppTest", "json", "C", 76, pageSize, pageNo
            );

            HeritageResponse resp = restTemplate.getForObject(
                    req.toUri(), HeritageResponse.class
            );

            List<HeritageResponse.HeritageItem> items =
                    resp.wrapper().body().items().itemList();

            if (items.isEmpty()) break;

            List<Heritage> entities = items.stream()
                    .map(this::toEntity)
                    .toList();

            heritageRepository.saveAll(entities);

            // 마지막 페이지면 종료
            if (pageNo * pageSize >= resp.wrapper().body().totalCount()) {
                break;
            }
            pageNo++;
        }
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return List.of();
    }

    private Heritage toEntity(HeritageResponse.HeritageItem item) {
        return Heritage.builder()
                .contentId   (parseLongSafe   (item.contentid()))
                .title       (item.title())
                .addr1       (item.addr1())
                .addr2       (item.addr2())
                .areaCode    (parseIntegerSafe(item.areacode()))
                .cat1        (item.cat1())
                .cat2        (item.cat2())
                .cat3        (item.cat3())
                .contentTypeId(parseIntegerSafe(item.contenttypeid()))
                .createdTime (parseDateTimeSafe(item.createdtime()))
                .firstImage  (item.firstimage())
                .firstImage2 (item.firstimage2())
                .copyrightDivCd(item.cpyrhtDivCd())
                .mapX        (parseDoubleSafe (item.mapx()))
                .mapY        (parseDoubleSafe (item.mapy()))
                .mLevel      (parseIntegerSafe(item.mlevel()))
                .modifiedTime(parseDateTimeSafe(item.modifiedtime()))
                .sigunguCode (parseIntegerSafe(item.sigungucode()))
                .tel         (item.tel())
                .zipcode     (item.zipcode())
                .lDongRegnCd (parseIntegerSafe(item.lDongRegnCd()))
                .lDongSignguCd(parseIntegerSafe(item.lDongSignguCd()))
                .lclsSystm1  (item.lclsSystm1())
                .lclsSystm2  (item.lclsSystm2())
                .lclsSystm3  (item.lclsSystm3())
                .build();
    }

    // LocalDateTime 안전 파싱 메서드 예시
    private LocalDateTime parseDateTimeSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDateTime.parse(s, DTF);
        } catch (DateTimeParseException e) {
            return null;
        }
    }


    /** 빈(null or "") 이면 null, 아니면 Long 으로 파싱 */
    private Long parseLongSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** 빈(null or "") 이면 null, 아니면 Integer 로 파싱 */
    private Integer parseIntegerSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** 빈(null or "") 이면 null, 아니면 Double 로 파싱 */
    private Double parseDoubleSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}