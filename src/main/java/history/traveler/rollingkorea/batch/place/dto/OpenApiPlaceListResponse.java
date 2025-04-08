package history.traveler.rollingkorea.batch.place.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "result")
public record OpenApiPlaceListResponse(
        @JacksonXmlElementWrapper(useWrapping = false)
        List<OpenApiPlaceResponse> item
) {}