package history.traveler.rollingkorea.global.config.security;

import history.traveler.rollingkorea.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;

    public String generateToken(User user) {
        // 예: 사용자 ID나 이메일 등을 기반으로 JWT 생성
        String subject = user.getLoginId(); // 또는 user.getEmail()
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600 * 1000); // 1시간

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
