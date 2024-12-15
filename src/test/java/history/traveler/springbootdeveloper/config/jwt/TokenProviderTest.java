package history.traveler.springbootdeveloper.config.jwt;


import history.traveler.springbootdeveloper.domain.User;
import history.traveler.springbootdeveloper.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;


    @DisplayName("generateToken(): you can generate a token by providing user information and an expiration period. ")
    @Test
    void generateToken(){
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id" , Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): Validation fails when the token is expired.")
    @Test
    void validToken_invalidToken(){
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): Validation success when the token is fine.")
    @Test
    void validToken_validToken(){
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): You can retrieve authentication information using a token.")
    @Test
    void getAuthentication() {
        //given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        //when
        Authentication authentication = tokenProvider.getAuthentication(token);
        //then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserId(): A token can also be used to fetch the user's ID.")
    @Test
    void getUserId(){
        //given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        //when
        Long userIdByToken = tokenProvider.getUserId(token);
        //then
        assertThat(userIdByToken).isEqualTo(userId);


    }
}
