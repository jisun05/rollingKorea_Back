package history.traveler.rollingkorea.heritage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeritageService {

    public List<HeritageRequest> getHeritages() throws Exception {
        String url = "http://www.khs.go.kr/cha/SearchKindOpenapiList.do?pageUnit=10&ccbaCncl=N&ccbaKdcd=11&ccbaCtcd=11";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        JsonNode items = root.path("response");

        List<HeritageRequest> list = new ArrayList<>();
        for (JsonNode item : items) {
            list.add(new HeritageRequest(
                    item.path("ccsiName").asText(),
                    item.path("ccbaCtcdNm").asText(),
                    item.path("imageUrl").asText(),
                    item.path("latitude").asText(),
                    item.path("longitude").asText()
            ));
        }

        return list;
    }
}
