package history.traveler.rollingkorea.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) CORS 설정
                .cors().and()
                // 2) CSRF 비활성화 (API 서버라면 토큰 방식으로 대체)
                .csrf().disable()
                // 3) 세션을 사용하지 않음 (JWT 방식)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 4) 엔드포인트별 허용/인가 규칙
                .authorizeHttpRequests(auth -> auth
                        // pre-flight 요청은 모두 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 구글 로그인, 인증 관련 엔드포인트는 모두 허용
                        .requestMatchers(HttpMethod.POST, "/api/google/login").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        // 프로필 조회 등 그 외 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // 5) JWT 필터 등록: UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:3000"));   // 프론트 도메인
        cfg.setAllowedMethods(List.of("GET","POST","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
