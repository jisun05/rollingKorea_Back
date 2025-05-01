package history.traveler.rollingkorea.heritage.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.dto.response.KindOpenApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HeritageService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final XmlMapper xmlMapper;

    public HeritageService(
            RestTemplate restTemplate,
            @Value("${heritage.api.url}") String apiUrl,
            XmlMapper xmlMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.xmlMapper = xmlMapper;
    }

    public List<HeritageRequest> getHeritages() throws Exception {
        // ↓ JSON 디버그: 실제 JSON payload 찍기
        String json = restTemplate.getForObject(
                apiUrl + "?pageUnit=10&ccbaCncl=N&ccbaKdcd=11&ccbaCtcd=11&_type=json",
                String.class);
        System.out.println("🎯 RAW JSON:\n" + json);

        // ↓ XML 디버그: 실제 XML payload 찍기
        String xml = restTemplate.getForObject(
                apiUrl + "?pageUnit=10&ccbaCncl=N&ccbaKdcd=11&ccbaCtcd=11",
                String.class);
        System.out.println("🎯 RAW XML:\n" + xml);

        // 기존 매핑: KindOpenApiResponse로 파싱
        KindOpenApiResponse resp = xmlMapper.readValue(xml, KindOpenApiResponse.class);
        List<HeritageRequest> list = resp == null ? List.of() : resp.items();
        System.out.println("▶ parsed items count = " + list.size());
        return list;
    }
}

