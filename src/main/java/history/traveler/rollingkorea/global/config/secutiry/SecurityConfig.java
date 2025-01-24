package history.traveler.rollingkorea.global.config.secutiry;
import history.traveler.rollingkorea.global.config.secutiry.token.TokenProvider;
import history.traveler.rollingkorea.global.config.secutiry.token.TokenAuthenticationFilter;
import history.traveler.rollingkorea.global.config.secutiry.token.RefreshTokenRepository;
import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;



import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults()) // httpBasic() 대체
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 리다이렉트할 URL
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sessionManagement() 대체
                )
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize // authorizeRequests() 대체
                        .requestMatchers("/api/token").permitAll()
                        .requestMatchers("/api/place").permitAll()
                        .requestMatchers("/api/images/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()

                )
                .oauth2Login(oauth2 -> oauth2 // OAuth2 로그인 설정
                        .loginPage("/login") // 로그인 페이지 URL
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                        )
                        .successHandler(oAuth2SuccessHandler())
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserCustomService))
                                .successHandler(oAuth2SuccessHandler())


                )
                .formLogin(form -> form // 폼 로그인 설정
                        .loginPage("/login") // 로그인 페이지 URL
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 리다이렉트할 URL
                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
                        .permitAll() // 모든 사용자에게 로그인 페이지 접근 허용
                )
                .exceptionHandling(exception -> exception // 예외 처리 설정
                        .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**"))
                )
                .csrf(csrf -> csrf // CSRF 보호 설정
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
