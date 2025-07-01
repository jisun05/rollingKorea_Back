// src/main/java/history/traveler/rollingkorea/config/RestConfig.java
package history.traveler.rollingkorea.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder,
            Jackson2ObjectMapperBuilder jacksonBuilder
    ) {
        // (1) 이미지 바이너리용 컨버터
        ByteArrayHttpMessageConverter imageConv = new ByteArrayHttpMessageConverter();
        imageConv.setSupportedMediaTypes(List.of(
                MediaType.IMAGE_JPEG,                   // "image/jpeg"
                MediaType.IMAGE_PNG,                    // "image/png"
                MediaType.APPLICATION_OCTET_STREAM,     // fallback
                MediaType.valueOf("image/jpg")          // <-- 추가
        ));

        // (2) JSON 전용 컨버터
        MappingJackson2HttpMessageConverter jsonConv =
                new MappingJackson2HttpMessageConverter(jacksonBuilder.build());

        // (3) XML 전용 컨버터
        XmlMapper xmlMapper = jacksonBuilder
                .createXmlMapper(true)
                .build();
        xmlMapper.registerModule(new JaxbAnnotationModule());
        MappingJackson2XmlHttpMessageConverter xmlConv =
                new MappingJackson2XmlHttpMessageConverter(xmlMapper);

        // (4) 텍스트 컨버터
        StringHttpMessageConverter textConv = new StringHttpMessageConverter();

        return restTemplateBuilder
                .messageConverters(imageConv, jsonConv, xmlConv, textConv)
                .build();
    }
}
