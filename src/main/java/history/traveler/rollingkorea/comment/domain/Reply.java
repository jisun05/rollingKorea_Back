package history.traveler.rollingkorea.comment.domain;
import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPLY")
@Getter
@Setter
@AllArgsConstructor //이것만 사용하면 모든 필드를 매개변수로 받는 생성자가 생기지만 기본생성자는 생기지 않음
//JPA에서는 Entity 클래스가 기본 생성자를 가져야한다는 규칙이 있음,이 기본생성자는 JPA가 Entity를 인스턴스화할 때 필요
@NoArgsConstructor // 기본생성자를 추가해 JPA의 요구 사항을 충족하게 한다
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;


    @ManyToOne // Reply는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    //referencedColumnName = "user_id": 외래 키가 참조하는 엔티티(User) pk 컬럼 이름을 지정
    //insertable = false: 이 컬럼이 삽입(insert) 시에 사용되지 않도록 설정
    //updatable = false: Comment 엔티티 업데이트될 때 user_id 값 변경불가, 처음 설정된 값 유지
    private User user; // User 엔티티와의 관계를 나타내는 필드


    @ManyToOne // Reply는 여러 개가 하나의 Comment에 속할 수 있는 관계
    @JoinColumn(name = "comment_id", nullable = true, referencedColumnName = "comment_id", updatable = false )
    private Comment comment;

    private String content;
    private String nickname;
    private Long likes;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public Reply(User user, Comment comment, String content,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.comment = comment;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // create reply
    public static Reply createReply(User user, Comment comment, ReplyCreateRequest replyCreateRequest) {
        System.out.println("Comment ID in createReply: " + comment.getCommentId()); // 디버깅용
        return Reply.builder()
                .user(user)
                .comment(comment)
                .content(replyCreateRequest.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void edit(@NotNull(message = "write your reply") @Size(max = 500, message = "max size is 500") String content) {

            this.content = content;
            this.updatedAt = LocalDateTime.now();

    }
}
