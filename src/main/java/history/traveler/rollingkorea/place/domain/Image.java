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

    @Column(name = "image_path", nullable = false)   //사진경로
    private String imagePath;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public Image(String imagePath, Place place) {
        this.imagePath = imagePath;
        this.place = place;
    }

    // 연관관계 편의 메서드 (양방향 매핑)
    public void setPlace(Place place) {
        this.place = place;
        place.getImages().add(this);
    }

}
