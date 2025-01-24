package history.traveler.rollingkorea.place.domain;


import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIKE_PLACE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikePlace {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_place_id")
    private Long likePlaceId;

    @ManyToOne // LiKE_PLACE는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;


    @ManyToOne //LIKE_PLACE는 여러 개가 하나의 COMMENT에 속할 수 있는 관계
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id", insertable = false, updatable = false)
    private Comment comment;


}
