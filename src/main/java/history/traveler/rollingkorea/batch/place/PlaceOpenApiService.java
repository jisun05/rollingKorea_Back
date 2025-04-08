package history.traveler.rollingkorea.batch.place;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import history.traveler.rollingkorea.batch.place.dto.OpenApiPlaceResponse;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceOpenApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PlaceRepository placeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "http://www.khs.go.kr/cha/SearchKindOpenapiList.do";

    public void fetchAndSave() {
        // 시도 코드 + 시군구 코드는 예시로 서울 (11, 110)
        URI uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("ccbaCtcd", "11")       // 서울
                .queryParam("ccbaLcto", "110")      // 서울시 전체
                .queryParam("pageIndex", 1)
                .queryParam("pageUnit", 100)
                .queryParam("chnlType", "json")     // JSON 응답 명시 (추정)
                .build()
                .toUri();

        log.info("🌐 외부 API 호출 URI: {}", uri);

        try {
            String json = restTemplate.getForObject(uri, String.class);
            JsonNode root = objectMapper.readTree(json);

            JsonNode dataArray = root.path("data"); // 실제 응답 구조 확인 필요

            if (!dataArray.isArray()) {
                log.warn("❌ 예상한 'data' 배열이 아님: {}", dataArray);
                return;
            }

            List<OpenApiPlaceResponse> responseList = objectMapper
                    .readerForListOf(OpenApiPlaceResponse.class)
                    .readValue(dataArray);

            List<Place> places = responseList.stream()
                    .map(OpenApiPlaceResponse::toEntity)
                    .toList();

            placeRepository.saveAll(places);
            log.info("✅ 저장 완료: {}개", places.size());

        } catch (Exception e) {
            log.error("❌ 외부 API 처리 중 예외 발생", e);
        }
    }
}
