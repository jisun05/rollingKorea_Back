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
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "nick_name")
    private String nickName;
    private String password;
    @Column(name = "provider_id")
    private String providerId;
    private String enabled;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Builder
    //모든 필드를 받는 생성자
    public User(String userId, String email, String userName, String nickName, String password, String providerId, String enabled, LocalDateTime createDate, LocalDateTime updateDate, LocalDateTime deleteDate) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.providerId = providerId;
        this.enabled = enabled;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
    }




    public User update(String nickName) {
        this.nickName = nickName;
        return this;
    }

}