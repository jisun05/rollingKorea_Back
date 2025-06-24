package history.traveler.rollingkorea.heritage.service;
import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import history.traveler.rollingkorea.heritage.controller.request.HeritageXmlWrapper;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HeritageService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final XmlMapper xmlMapper;
    private final HeritageRepository heritageRepository;



    public HeritageService(
            RestTemplate restTemplate,
            @Value("${heritage.api.url}") String apiUrl,
            XmlMapper xmlMapper, HeritageRepository heritageRepository
    ) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.xmlMapper = xmlMapper;
        this.heritageRepository = heritageRepository;
    }

    public List<HeritageRequest> getHeritages() throws Exception {
        String xml = restTemplate.getForObject(
                apiUrl + "?pageUnit=10&ccbaCncl=N&ccbaKdcd=11&ccbaCtcd=11",
                String.class
        );

        HeritageXmlWrapper wrapper = xmlMapper.readValue(xml, HeritageXmlWrapper.class);
        return wrapper.item(); // record 필드
    }

    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }
}
