package history.traveler.springbootdeveloper.domain;


import jakarta.persistence.*;
import lombok.Getter;



@Getter
@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placeName", nullable = false)
    private String placeName;

    @Column(name = "website")
    private String website; // 장소 관련 공식 홈페이지

    @Column(name = "latitude")
    private double latitude; // 장소의 위도

    @Column(name = "longitude")
    private double longitude; // 장소의 경도

    @Column(name = "image_url")
    private String imageUrl; // 장소의 대표 이미지

    @Column(name = "region")
    private String region;

    // 생성자
    public Place() {
    }

    public Place(Long id, String placeName, String website, double latitude, double longitude, String imageUrl) {
        this.id = id;
        this.placeName = placeName;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
    }
}