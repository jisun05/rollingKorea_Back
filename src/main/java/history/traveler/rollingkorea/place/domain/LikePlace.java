package history.traveler.rollingkorea.place.domain;
import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "LIKE_PLACE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //상속받은 서브클래스에서만 기본 생성자를 사용할 수 있도록 제한=>외부에서 직접 인스턴스를 생성하는 것을 방지
public class LikePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_place_id")
    private Long likePlaceId;

    @ManyToOne // LiKE_PLACE는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private Long placeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder  //빌더패턴으로 생성자 생성
    //모든 필드를 한 번에 설정할 필요가 없고, 필요한 필드만 설정하여 객체를 생성 가능
    //JPA 엔티티에서 기본 생성자는 필요하지만, 필드가 많은 경우 빌더 패턴을 통해 객체를 생성하는 것이  편리
    public LikePlace(Long likePlaceId, User user, Long placeId, LocalDateTime createdAt) {
        this.likePlaceId = likePlaceId;
        this.user = user;
        this.placeId = placeId;
        this.createdAt = createdAt;
    }
}
