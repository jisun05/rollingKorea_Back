package history.traveler.rollingkorea.heritage.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;

import java.util.List;
// response 전체를 감싸는 클래스
@JacksonXmlRootElement(localName = "response")
public class KindOpenApiResponse {

    @JacksonXmlProperty(localName = "body")
    private Body body;

    public List<HeritageRequest> items() {
        if (body == null || body.getItems() == null) {
            return List.of();
        }
        return body.getItems();
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Body {
        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        private List<HeritageRequest> items;

        public List<HeritageRequest> getItems() {
            return items;
        }

        public void setItems(List<HeritageRequest> items) {
            this.items = items;
        }
    }
}
