package history.traveler.rollingkorea.heritage.controller.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;

import java.util.List;

@JacksonXmlRootElement(localName = "items")
public class HeritageXmlWrapper {
        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<HeritageRequest> item;

        public List<HeritageRequest> getItem() {
                return item;
        }

        public void setItem(List<HeritageRequest> item) {
                this.item = item;
        }
}
