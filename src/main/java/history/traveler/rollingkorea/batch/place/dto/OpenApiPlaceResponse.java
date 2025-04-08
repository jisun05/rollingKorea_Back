package history.traveler.rollingkorea.batch.place.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import history.traveler.rollingkorea.place.domain.Place;

import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "item")
public record OpenApiPlaceResponse(
        @JacksonXmlProperty(localName = "ccbaMnm1") String name,
        @JacksonXmlProperty(localName = "ccbaCtcdNm") String region,
        @JacksonXmlProperty(localName = "ccbaAdmin") String website,
        @JacksonXmlProperty(localName = "ccbaCpno") String description,
        @JacksonXmlProperty(localName = "longitude") double longitude,
        @JacksonXmlProperty(localName = "latitude") double latitude,
        @JacksonXmlProperty(localName = "regDt") String regDt
) {
    public Place toEntity() {
        return Place.builder()
                .placeName(name)
                .region(region)
                .website(website)
                .placeDescription(description)
                .longitude(longitude)
                .latitude(latitude)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
