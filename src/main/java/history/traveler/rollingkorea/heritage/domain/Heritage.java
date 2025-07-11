package history.traveler.rollingkorea.heritage.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name = "heritage")
public class Heritage {

    @Id
    @Column(name = "content_id")
    private Long contentId;

    private String title;
    private String addr1;
    private String addr2;
    private Integer areaCode;
    private String cat1;
    private String cat2;
    private String cat3;
    private Integer contentTypeId;
    private LocalDateTime createdTime;
    private String copyrightDivCd;
    private Double mapX;
    private Double mapY;
    private Integer mLevel;
    private LocalDateTime modifiedTime;
    private Integer sigunguCode;
    private String tel;
    private String zipcode;
    private Integer lDongRegnCd;
    private Integer lDongSignguCd;
    private String lclsSystm1;
    private String lclsSystm2;
    private String lclsSystm3;

    /** 이미지 URL을 별도 테이블에 모아둔다 */
    @ElementCollection
    @CollectionTable(name = "heritage_image",
            joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    protected Heritage() {}

    @Builder
    public Heritage(Long contentId,
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
                    List<String> imageUrls
    ) {
        this.contentId     = contentId;
        this.title         = title;
        this.addr1         = addr1;
        this.addr2         = addr2;
        this.areaCode      = areaCode;
        this.cat1          = cat1;
        this.cat2          = cat2;
        this.cat3          = cat3;
        this.contentTypeId = contentTypeId;
        this.createdTime   = createdTime;
        this.copyrightDivCd= copyrightDivCd;
        this.mapX          = mapX;
        this.mapY          = mapY;
        this.mLevel        = mLevel;
        this.modifiedTime  = modifiedTime;
        this.sigunguCode   = sigunguCode;
        this.tel           = tel;
        this.zipcode       = zipcode;
        this.lDongRegnCd   = lDongRegnCd;
        this.lDongSignguCd = lDongSignguCd;
        this.lclsSystm1    = lclsSystm1;
        this.lclsSystm2    = lclsSystm2;
        this.lclsSystm3    = lclsSystm3;
        this.imageUrls     = imageUrls != null ? imageUrls : new ArrayList<>();
    }
}
