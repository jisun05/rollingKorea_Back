package history.traveler.rollingkorea.place.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place")
public class Place {

    /**
     * 실제 DB 의 PK(id AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 외부 API(heritage) 상의 고유 ID
     */
    @Column(name = "content_id", nullable = false, unique = true)
    private Long contentId;

    private String title;
    private String addr1;
    private String addr2;

    @Column(name = "area_code")
    private Integer areaCode;

    private String cat1;
    private String cat2;
    private String cat3;

    @Column(name = "content_type_id")
    private Integer contentTypeId;

    @Column(name = "created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdTime;

    @Column(name = "copyright_div_cd")
    private String copyrightDivCd;

    @Column(name = "map_x")
    private Double mapX;

    @Column(name = "map_y")
    private Double mapY;

    @Column(name = "m_level")
    private Integer mLevel;

    @Column(name = "modified_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedTime;

    @Column(name = "sigungu_code")
    private Integer sigunguCode;

    private String tel;
    private String zipcode;

    @Column(name = "l_dong_regn_cd")
    private Integer lDongRegnCd;

    @Column(name = "l_dong_signgu_cd")
    private Integer lDongSignguCd;

    private String lclsSystm1;
    private String lclsSystm2;
    private String lclsSystm3;

    /** 외부 API에서 가져온 데이터를 Place에 복사한 시각 */
    @Column(name = "imported_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime importedAt;

    /** 좋아요 관계 */
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikePlace> likePlaces = new ArrayList<>();

    /** 이미지 관계 */
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Place(
            Long contentId,
            String title,
            String addr1,
            String addr2,
            Integer areaCode,
            String cat1,
            String cat2,
            String cat3,
            Integer contentTypeId,
            LocalDateTime createdTime,
            String copyrightDivCd,
            Double mapX,
            Double mapY,
            Integer mLevel,
            LocalDateTime modifiedTime,
            Integer sigunguCode,
            String tel,
            String zipcode,
            Integer lDongRegnCd,
            Integer lDongSignguCd,
            String lclsSystm1,
            String lclsSystm2,
            String lclsSystm3,
            LocalDateTime importedAt
    ) {
        this.contentId = contentId;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areaCode = areaCode;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contentTypeId = contentTypeId;
        this.createdTime = createdTime;
        this.copyrightDivCd = copyrightDivCd;
        this.mapX = mapX;
        this.mapY = mapY;
        this.mLevel = mLevel;
        this.modifiedTime = modifiedTime;
        this.sigunguCode = sigunguCode;
        this.tel = tel;
        this.zipcode = zipcode;
        this.lDongRegnCd = lDongRegnCd;
        this.lDongSignguCd = lDongSignguCd;
        this.lclsSystm1 = lclsSystm1;
        this.lclsSystm2 = lclsSystm2;
        this.lclsSystm3 = lclsSystm3;
        this.importedAt = importedAt;
    }

    /** 외부 API 데이터로 Place 생성 */
    public static Place fromHeritage(Heritage heritage) {
        if (heritage.getContentId() == null) {
            throw new IllegalStateException("Heritage.contentId is null!");
        }
        return Place.builder()
                .contentId(heritage.getContentId())
                .title(heritage.getTitle())
                .addr1(heritage.getAddr1())
                .addr2(heritage.getAddr2())
                .areaCode(heritage.getAreaCode())
                .cat1(heritage.getCat1())
                .cat2(heritage.getCat2())
                .cat3(heritage.getCat3())
                .contentTypeId(heritage.getContentTypeId())
                .createdTime(heritage.getCreatedTime())
                .copyrightDivCd(heritage.getCopyrightDivCd())
                .mapX(heritage.getMapX())
                .mapY(heritage.getMapY())
                .mLevel(heritage.getMLevel())
                .modifiedTime(heritage.getModifiedTime())
                .sigunguCode(heritage.getSigunguCode())
                .tel(heritage.getTel())
                .zipcode(heritage.getZipcode())
                .lDongRegnCd(heritage.getLDongRegnCd())
                .lDongSignguCd(heritage.getLDongSignguCd())
                .lclsSystm1(heritage.getLclsSystm1())
                .lclsSystm2(heritage.getLclsSystm2())
                .lclsSystm3(heritage.getLclsSystm3())
                .importedAt(LocalDateTime.now())
                .build();
    }

    /** 프론트용 편집 메서드 */
    public void update(PlaceEditRequest dto) {
        this.title = dto.title();
        this.addr1 = dto.addr1();
        this.addr2 = dto.addr2();
        this.modifiedTime = LocalDateTime.now();
    }
}
