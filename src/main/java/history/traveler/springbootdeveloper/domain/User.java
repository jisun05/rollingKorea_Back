package history.traveler.springbootdeveloper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;


import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;
    private int enabled;
    private String role;
    private Timestamp createDate;
    // 구글, 네이버, 자체 로그인 정보 저장
    private String provider;
    private String providerId;

    @Builder
    public User(String username, String email, String password, int enabled, String role,
                String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }


}