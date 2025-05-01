package history.traveler.rollingkorea.heritage.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;

import java.util.List;

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

    // ─── Body 엔벨로프 ───────────────────────────────────────────────

    public static class Body {
        @JacksonXmlElementWrapper(localName = "items")  // <items> … </items>
        @JacksonXmlProperty(localName = "item")         // 내부의 여러 <item> 요소
        private List<HeritageRequest> items;

        // Jackson용 getter/setter
        public List<HeritageRequest> getItems() {
            return items;
        }
        public void setItems(List<HeritageRequest> items) {
            this.items = items;
        }
    }

    // Jackson용 getter/setter
    public Body getBody() {
        return body;
    }
    public void setBody(Body body) {
        this.body = body;
    }
}
