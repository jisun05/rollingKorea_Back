package history.traveler.rollingkorea.heritage.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder,
            Jackson2ObjectMapperBuilder jacksonBuilder
    ) {
        // 1) JSON 전용 컨버터 (기본 Spring 설정 말고, 명시적으로 하나 만듭니다)
        MappingJackson2HttpMessageConverter jsonConv =
                new MappingJackson2HttpMessageConverter(jacksonBuilder.build());

        // 2) XML 전용 컨버터
        XmlMapper xmlMapper = jacksonBuilder
                .createXmlMapper(true)
                .build();
        xmlMapper.registerModule(new JaxbAnnotationModule());
        MappingJackson2XmlHttpMessageConverter xmlConv =
                new MappingJackson2XmlHttpMessageConverter(xmlMapper);

        // 3) String(plain text) 컨버터
        StringHttpMessageConverter textConv = new StringHttpMessageConverter();

        // 4)순서를 JSON → XML → 텍스트 로 고정
        return restTemplateBuilder
                .messageConverters(jsonConv, xmlConv, textConv)
                .build();
    }
}
