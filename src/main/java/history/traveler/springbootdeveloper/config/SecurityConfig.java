package history.traveler.springbootdeveloper.config;

import history.traveler.springbootdeveloper.service.PrincipalDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
public class SecurityConfig {

    // 해당 메서드의 리턴되는 오브젝트를 IOC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PrincipalDetailsService principalDetailsService) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().permitAll();
        });

        http.formLogin(form -> {
            form.loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/");
        });
        http.oauth2Login(form -> form.loginPage("/loginForm")
                .userInfoEndpoint(userInfoEndpointConfig -> {userInfoEndpointConfig.userService((OAuth2UserService<OAuth2UserRequest, OAuth2User>) principalDetailsService);
                }));

        return http.build();
    }

}