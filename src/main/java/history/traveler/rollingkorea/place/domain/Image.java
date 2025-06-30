package history.traveler.rollingkorea.place.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Lob
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    // place 테이블의 PK가 content_id 로 바뀜
    @JoinColumn(name = "content_id", nullable = false, referencedColumnName = "content_id")
    private Place place;

    @Builder
    public Image(byte[] imageData, Place place) {
        this.imageData = imageData;
        this.place = place;
    }
}
