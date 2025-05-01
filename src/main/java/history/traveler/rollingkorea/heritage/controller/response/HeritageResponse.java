package history.traveler.rollingkorea.heritage.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import history.traveler.rollingkorea.heritage.domain.Heritage;

@JacksonXmlRootElement(localName = "item")  // XML의 <item>…</item> 블록과 대응
public record HeritageResponse(

        @JacksonXmlProperty(localName = "ccsiName")
        String ccsiName,

        @JacksonXmlProperty(localName = "ccbaCtcdNm")
        String ccbaCtcdNm,

        @JacksonXmlProperty(localName = "imageUrl")
        String imageUrl,

        @JacksonXmlProperty(localName = "latitude")
        String latitude,

        @JacksonXmlProperty(localName = "longitude")
        String longitude

) {
    public static HeritageResponse from(Heritage heritage) {
        return new HeritageResponse(
                heritage.getCcsiName(),
                heritage.getCcbaCtcdNm(),
                heritage.getImageUrl(),
                heritage.getLatitude(),
                heritage.getLongitude()
        );
    }
}
