package history.traveler.rollingkorea.heritage.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import history.traveler.rollingkorea.heritage.domain.Heritage;

@JacksonXmlRootElement(localName = "item")
public record HeritageRequest(
        @JacksonXmlProperty(localName = "title") String title,
        @JacksonXmlProperty(localName = "addr1") String addr1,
        @JacksonXmlProperty(localName = "mapx") String mapx,
        @JacksonXmlProperty(localName = "mapy") String mapy,
        @JacksonXmlProperty(localName = "firstimage") String imageUrl
) {
    public Heritage toEntity() {
        return new Heritage(title, addr1, imageUrl, mapx, mapy);
    }
}
