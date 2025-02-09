package history.traveler.rollingkorea.place.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //상속받은 서브클래스에서만 기본 생성자를 사용할 수 있도록 제한=>외부에서 직접 인스턴스를 생성하는 것을 방지
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "place_name", nullable = false, unique = true)
    private String placeName;

    @Column(name = "website")
    private String website; // 장소 관련 공식 홈페이지
    @Column(name = "latitude")
    private double latitude; // 장소의 위도

    @Column(name = "longitude")
    private double longitude; // 장소의 경도

    @Column(name = "place_description")
    private String placeDescription;

    @Column(name = "region")
    private String region;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "count_like")
    private String countLike;

    @Builder
    public Place(Long placeId, String placeName, String website, double latitude, double longitude, String region, String placeDescription, LocalDateTime createdAt, LocalDateTime updatedAt, String countLike) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.placeDescription = placeDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.countLike = countLike;
    }

    // 업데이트 ( 이미지는 이미지 엔티티에서 처리)
    public void update(PlaceEditRequest placeEditRequest) {
        this.placeName = placeEditRequest.placeName();
        this.region = placeEditRequest.region();
        this.latitude = placeEditRequest.latitude();
        this.longitude = placeEditRequest.longitude();
        this.placeDescription = placeEditRequest.placeDescription();
    }
}