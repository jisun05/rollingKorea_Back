package history.traveler.rollingkorea.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1) 완전 재설정: 기존 모두 제거
        converters.clear();

        // 2) JSON 바디 전용 컨버터만 등록
        converters.add(new MappingJackson2HttpMessageConverter());

        // 3) 필요하면 String 등 다른 기본 컨버터도 직접 등록
        converters.add(new StringHttpMessageConverter());
    }
}
