package history.traveler.rollingkorea.article.domain;


import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // 이 클래스가 JPA 엔티티임을 나타냄
@Table(name = "CONTACTUS") // 데이터베이스의 CONTACTUS 테이블과 매핑
@Getter // Lombok을 사용하여 getter 메서드를 자동 생성
@Setter
public class ContactUs {

    @Id // 이 필드가 엔티티의 기본 키(pk)임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)// pk 값을 자동 생성하도록 설정
    @Column(name = "contactUs_id") // 데이터베이스의 contactUs_id 컬럼과 매핑
    private Long contactUsId;

    @ManyToOne // ContactUs는 여러 개가 하나의 User에 속할 수 있는 관계
    //ContactUs 테이블에서 user_id라는 컬럼이 외래 키로 사용됨, 참조하는 엔티티(User)의 기본 키 컬럼 이름을 지정(User 엔티티의 user_id 컬럼이 기본 키로 사용)
    //JPA가 이 외래 키 컬럼을 삽입할 때 사용하지 않도록 설정(ContactUs 엔티티를 데이터베이스에 저장할 때 user_id 값을 사용자가 직접 삽입하지 않고 연관된 User 엔터티를 통해 자동으로 설정됨)
   //ContactUs 엔티티가 데이터베이스에서 업데이트될 때 user_id 값을 변경하지 않음
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    // user_id 컬럼과 User 엔티티의 user_id 컬럼을 매핑
    private User user; // User 엔티티와의 관계를 나타내는 필드


    private String content;

    @Column(name = "created_at")// 데이터베이스의 created_at 컬럼과 매핑

    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
