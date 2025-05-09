package history.traveler.rollingkorea.question.domain;

import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Table(name = "CONTACTUS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactUs_id")
    private Long contactUsId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "list_order")
    private Long listOrder;

    @Embedded
    private File file;

    // 빌더 패턴을 사용한 생성자
    @Builder
    public ContactUs(User user, String content, Long parentId, Long listOrder, File file) {
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.listOrder = listOrder;
        this.file = file;
        this.createdAt = LocalDateTime.now(); // Set created timestamp
        this.updatedAt = LocalDateTime.now(); // Set updated timestamp
    }

    public static ContactUs createContactUs(User user, ContactUsCreateRequest contactUsCreateRequest) throws IOException {
        return ContactUs.builder()
                .user(user)
                .content(contactUsCreateRequest.content())
                .file(new File(contactUsCreateRequest.fileData(), contactUsCreateRequest.fileName()))
                .build();
    }

    public void editContactUs(ContactUsEditRequest contactUsEditRequest) {
        this.content = contactUsEditRequest.content();
        this.updatedAt = LocalDateTime.now(); // Update timestamp on edit
    }

    public void updateFile(File file) {
        this.file = file;
        this.updatedAt = LocalDateTime.now();
    }

}
