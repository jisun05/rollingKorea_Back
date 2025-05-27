package history.traveler.rollingkorea.user.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //상속받은 서브클래스에서만 기본 생성자를 사용할 수 있도록 제한=>외부에서 직접 인스턴스를 생성하는 것을 방지
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nickname")
    private String nickname;

    @Nullable
    @Column(name = "location")
    private String location;

    @Nullable
    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Nullable
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;    //로그인타입 ( NO_SOCIAL , GOOGLE )

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User(Long userId) {
    }



    //withdraw
    public void userDelete(){
        this.deletedAt = LocalDateTime.now();
    }



}