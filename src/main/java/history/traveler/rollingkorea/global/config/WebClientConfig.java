package history.traveler.rollingkorea.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .compress(true)   // 응답 압축 지원
                .wiretap(true);   // 로깅 활성화
        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
