package history.traveler.rollingkorea.place.domain;

import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LIKE_PLACE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_place_id")
    private Long likePlaceId;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    // place 테이블의 PK가 content_id 로 바뀜
    @JoinColumn(
            name = "content_id",
            referencedColumnName = "content_id",
            nullable = false
    )
    private Place place;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public LikePlace(Long likePlaceId, User user, Place place, LocalDateTime createdAt) {
        this.likePlaceId = likePlaceId;
        this.user = user;
        this.place = place;
        this.createdAt = createdAt;
    }

    public static LikePlace createLikePlace(User user, Place place) {
        if (user == null || place == null) {
            throw new IllegalArgumentException("User 또는 Place가 null이면 안 됩니다.");
        }
        return LikePlace.builder()
                .user(user)
                .place(place)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
