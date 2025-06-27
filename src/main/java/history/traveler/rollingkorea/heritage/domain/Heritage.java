package history.traveler.rollingkorea.heritage.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "heritage")
public class Heritage {

    @Id
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
    private String firstImage;
    private String firstImage2;
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

    protected Heritage() {}

    public Heritage(
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
            String firstImage,
            String firstImage2,
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
            String lclsSystm3
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
        this.firstImage = firstImage;
        this.firstImage2 = firstImage2;
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
    }
}
