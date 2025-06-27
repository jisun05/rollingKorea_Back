package history.traveler.rollingkorea.heritage.dto.request;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public record HeritageRequest(
        String serviceKey,
        String mobileOS,
        String mobileApp,
        String responseType,
        String arrange,
        int contentTypeId,
        int numOfRows,
        int pageNo
) {
    public URI toUri() {
        return UriComponentsBuilder
                .fromHttpUrl("https://apis.data.go.kr/B551011/EngService2/areaBasedList2")
                .queryParam("ServiceKey", serviceKey)
                .queryParam("MobileOS", mobileOS)
                .queryParam("MobileApp", mobileApp)
                .queryParam("_type", responseType)
                .queryParam("arrange", arrange)
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .encode()
                .build()
                .toUri();
    }
}
