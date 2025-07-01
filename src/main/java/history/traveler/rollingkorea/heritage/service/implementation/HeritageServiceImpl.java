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
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private static final Logger log = LoggerFactory.getLogger(HeritageServiceImpl.class);
    private final RestTemplate restTemplate;
    private final HeritageRepository heritageRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;

    @Value("${heritage.api.key}")
    private String serviceKey;

    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    @Transactional
    public void fetchAndSaveHeritagesFromTourAPI() throws Exception {
        int pageNo = 1, pageSize = 100;

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
                // 1) Save Heritage
                Heritage h = toEntity(item);
                heritageRepository.save(h);

                // 2) Convert & save Place
                Place place = Place.fromHeritage(h);
                placeRepository.save(place);

                // 3) Fetch image bytes and save Image entities
                List<String> urls = List.of(item.firstimage(), item.firstimage2()).stream()
                        .filter(u -> u != null && !u.isBlank())
                        .toList();

                for (String url : urls) {
                    byte[] data;
                    try {
                        data = restTemplate.getForObject(url, byte[].class);
                    } catch (UnknownContentTypeException ex) {
                        log.warn("URL='{}' 응답이 이미지가 아님: {}", url, ex.getMessage());
                        continue;
                    } catch (HttpClientErrorException.NotFound ex) {
                        // 404 Not Found
                        log.warn("URL='{}' 이미지 없음(404): {}", url, ex.getStatusCode());
                        continue;
                    } catch (HttpClientErrorException ex) {
                        // 그 외 4xx/5xx
                        log.warn("URL='{}' HTTP 오류: {} {}", url, ex.getStatusCode(), ex.getStatusText());
                        continue;
                    } catch (Exception ex) {
                        // 발행 가능한 모든 예외 보호
                        log.error("URL='{}' 처리 중 예외 발생", url, ex);
                        continue;
                    }
                    if (data != null && data.length > 0) {
                        Image img = Image.builder()
                                .imageData(data)
                                .place(place)
                                .build();
                        imageRepository.save(img);
                    }
                }
            }

            if (pageNo * pageSize >= body.totalCount()) break;
            pageNo++;
        }
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }

    private Heritage toEntity(HeritageResponse.HeritageItem item) {

        // 이미지 URL 리스팅
        List<String> urls = List.of(item.firstimage(), item.firstimage2()).stream()
                .filter(u -> u != null && !u.isBlank())
                .toList();

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
        try {
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseIntegerSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDoubleSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
