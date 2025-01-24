package history.traveler.rollingkorea.comment.domain;

import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "COMMENT")
@Getter
@Setter
public class Comment {

    @Id //이 엔터티의 기본키, 각 레코드를 고유하게 식별하는데 사용됨,유일성 보장
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //GeneratedValue는 기본키가 어떻게 생성되는지 정의, 기본키 자동증가를 위해서는 @Id와 같이 써야함
    //@Id가 없으면 JPA는 어떤 필드가 기본 키인지 알 수 없으므로, @GeneratedValue가 제대로 작동하지 않음
    @Column(name = "comment_id")
    private Long commentId;    //auto increment 쓸 경우 String 타입은 지양한다그래서 변경


    @ManyToOne // Comment는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user; // User 엔티티와의 관계를 나타내는 필드


    private String nickname;
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Long like;

}
