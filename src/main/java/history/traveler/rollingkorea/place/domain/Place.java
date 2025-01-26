package history.traveler.rollingkorea.place.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //상속받은 서브클래스에서만 기본 생성자를 사용할 수 있도록 제한=>외부에서 직접 인스턴스를 생성하는 것을 방지
@Table(name = "PLACE")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;
    @Column(name = "place_name", nullable = false)
    private String placeName;

    private String website; // 장소 관련 공식 홈페이지
    private double latitude; // 장소의 위도
    private double longitude; // 장소의 경도

    @Column(name = "image_path")
    @Setter
    private String imagePath; // 장소의 대표 이미지
    private String region;


    @Builder
    public Place(Long placeId, String placeName, String website, double latitude, double longitude, String imagePath, String region, LocalDateTime createdAt, LocalDateTime updatedAt, String countLike) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.countLike = countLike;
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "count_like")
    private String countLike;
}