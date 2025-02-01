package history.traveler.rollingkorea.comment.domain;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity// 이 클래스가 JPA 엔티티임을 나타냄
@Table(name = "COMMENT")  // 데이터베이스의 COMMENT 테이블과 매핑
@Getter // Lombok을 사용하여 getter 메서드를 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id //이 엔터티의 기본키, 각 레코드를 고유하게 식별하는데 사용됨,유일성 보장
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //GeneratedValue는 기본키가 어떻게 생성되는지 정의, 기본키 자동증가를 위해서는 @Id와 같이 써야함
    //@Id가 없으면 JPA는 어떤 필드가 기본 키인지 알 수 없으므로, @GeneratedValue가 제대로 작동하지 않음
    @Column(name = "comment_id")
    private Long commentId;    //auto increment 쓸 경우 String 타입은 지양한다그래서 변경


    @ManyToOne // Comment는 여러 개가 하나의 User에 속할 수 있는 관계
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user; // User 엔티티와의 관계를 나타내는 필드

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
    @Column
    private String nickname;
    @Column
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column
    private Long likes;

    @Builder
    public Comment(Long userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.getUser().setUserId(userId);
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //create comment
    public static Comment createComment(User user, CommentCreateRequest commentCreateRequest) {

        return Comment.builder()
                .userId(user.getUserId())
                .content(commentCreateRequest.content())
                .build();
    }

    //edit comment
    public void editComment(CommentEditRequest commentEditRequest) {
        this.content = commentEditRequest.content();
    }

}
