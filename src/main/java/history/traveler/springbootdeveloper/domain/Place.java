package history.traveler.springbootdeveloper.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "imagePath")
    @Setter
    private String imagePath; // 장소의 대표 이미지

    @Column(name = "region")
    private String region;

    @Column(name = "whichDay")
    private String whichDay;

}