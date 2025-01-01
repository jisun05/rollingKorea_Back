package history.traveler.springbootdeveloper.domain;

import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private String role;
    private LocalDateTime createDate;
    private String provider;
    private String providerId;
    private String nickname;

    @Builder
    // 모든 필드를 포함하는 생성자
    public User(String username, String email, String password, boolean enabled, String role,
                String provider, String providerId, LocalDateTime createDate, String nickname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.nickname = nickname;
    }


    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }

}