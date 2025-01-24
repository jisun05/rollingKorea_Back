package history.traveler.rollingkorea.place.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place")
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "count_like")
    private String countLike;
}