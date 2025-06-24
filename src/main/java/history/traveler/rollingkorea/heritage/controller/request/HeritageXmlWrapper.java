package history.traveler.rollingkorea.heritage.controller.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "items")  // XML 루트 태그 이름
public record HeritageXmlWrapper(
        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false) // <items><item>...</item><item>...</item></items>
        List<history.traveler.rollingkorea.heritage.dto.request.HeritageRequest> item
) {}
