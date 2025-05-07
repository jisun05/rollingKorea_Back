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
        KindOpenApiResponse resp = restTemplate.getForObject(
                apiUrl + "?pageUnit=10&ccbaCncl=N&ccbaKdcd=11&ccbaCtcd=11",
                KindOpenApiResponse.class
        );
        return resp == null ? List.of() : resp.items();
    }
}

