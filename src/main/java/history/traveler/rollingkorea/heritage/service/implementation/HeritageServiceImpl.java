package history.traveler.rollingkorea.heritage.service.implementation;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.dto.response.KindOpenApiResponse;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final HeritageRepository heritageRepository;

    @Value("${heritage.api.key}")
    private String serviceKey;

    @Override
    public void fetchAndSaveHeritagesFromTourAPI() throws Exception {
        // ✅ 인코딩하지 않은 원본 그대로 삽입
        String url = "https://apis.data.go.kr/B551011/EngService2/areaBasedList2"
                + "?numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest"
                + "&ServiceKey=" + serviceKey
                ;

        String xml = restTemplate.getForObject(url, String.class);

        // 확인용 출력 추가
        System.out.println("[DEBUG] 응답 XML >>>");
        System.out.println(xml);

        if (xml == null || xml.trim().isEmpty()) {
            throw new IllegalStateException("공공데이터 API에서 비어 있는 응답을 반환했습니다.");
        }

        // ✅ API XML 구조에 맞는 Wrapper 클래스 확인 필요
        KindOpenApiResponse response = xmlMapper.readValue(xml, KindOpenApiResponse.class);

        List<Heritage> entities = response.items().stream()
                .map(HeritageRequest::toEntity)
                .toList();

        heritageRepository.saveAll(entities);
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }
}