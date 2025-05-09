package history.traveler.rollingkorea.heritage.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder,
            Jackson2ObjectMapperBuilder jacksonBuilder) {

        // 1) Jackson2ObjectMapperBuilder로 XmlMapper 생성 (Record 지원 등 기본 모듈도 자동 등록)
        XmlMapper xmlMapper = jacksonBuilder
                .createXmlMapper(true)
                .build();
        // JAXB 어노테이션 지원 추가
        xmlMapper.registerModule(new JaxbAnnotationModule());

        // 2) 커스텀 XmlMapper 를 쓰는 XML 컨버터
        MappingJackson2XmlHttpMessageConverter xmlConv =
                new MappingJackson2XmlHttpMessageConverter(xmlMapper);

        // 3) (선택) JSON 전용 컨버터: 커스텀 ObjectMapper 가 필요하면 이렇게
        MappingJackson2HttpMessageConverter jsonConv =
                new MappingJackson2HttpMessageConverter(
                        jacksonBuilder.build()
                );

        // 4) 순수 텍스트 컨버터
        StringHttpMessageConverter stringConv = new StringHttpMessageConverter();

        // 5) RestTemplate 에 순서대로 등록
        return restTemplateBuilder
                .messageConverters(xmlConv, jsonConv, stringConv)
                .build();
    }

    /**
     * XmlMapper를 빈으로 등록해서 @Autowired XmlMapper xmlMapper;
     * 처럼 주입받을 수 있도록 합니다.
     */
    @Bean
    public XmlMapper xmlMapper(Jackson2ObjectMapperBuilderCustomizer customizer) {
        // Jackson2ObjectMapperBuilderCustomizer로 이미 등록된 모듈(ParameterNamesModule 등)을 가져오려면
        // Jackson2ObjectMapperBuilderBuilder를 쓰는 방법도 있지만,
        // 간단히 직접 생성 후 JAXB 모듈만 추가해 줍니다.
        XmlMapper mapper = new XmlMapper();
        // 레코드, 파라미터 이름, JDK8, JavaTime 모듈 등은
        // spring-boot-autoconfigure가 자동 등록해 줍니다.
        mapper.registerModule(new JaxbAnnotationModule());
        return mapper;
    }
}

