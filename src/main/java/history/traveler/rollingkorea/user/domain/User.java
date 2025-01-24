package history.traveler.rollingkorea.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor //기본생성자 자동생성

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_name")
    private String userName;

    private String nickname;
    private String password;
    @Column(name = "provider_id")
    private String providerId;
    private String enabled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    //모든 필드를 받는 생성자, 빌더패턴은 매개변수를 순서에 상관없이 설정할 수 있음
    public User(Long userId, String email, String userName, String nickname, String password, String providerId, String enabled, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
        this.password = password;
        this.providerId = providerId;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }




    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }

}