package history.traveler.rollingkorea.user.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 *
 *
 *
 *
 *
 *
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //상속받은 서브클래스에서만 기본 생성자를 사용할 수 있도록 제한=>외부에서 직접 인스턴스를 생성하는 것을 방지
@Table(name = "LOGIN_HISTORY")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_id")
    private Long loginHistoryId;


    @ManyToOne // LoginHistory는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user; // User 엔티티와의 관계를 나타내는 필드


    @Column(name = "login_date")
    private LocalDateTime loginDate;


    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "try_num")
    private String tryNum;

    @Builder
    public LoginHistory(Long loginHistoryId, User user, LocalDateTime loginDate, LocalDateTime loginTime, String ipAddress, String tryNum) {
        this.loginHistoryId = loginHistoryId;
        this.user = user;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.tryNum = tryNum;
    }
}
