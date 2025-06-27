package history.traveler.rollingkorea.heritage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record HeritageResponse(
        @JsonProperty("response") ResponseWrapper wrapper
) {
    public record ResponseWrapper(
            Header header,
            Body body
    ) {}

    public record Header(
            String resultCode,
            String resultMsg
    ) {}

    public record Body(
            Items items,
            int numOfRows,
            int pageNo,
            int totalCount
    ) {}

    public record Items(
            @JsonProperty("item")
            List<HeritageItem> itemList
    ) {}

    public record HeritageItem(
            String addr1,
            String addr2,
            String areacode,
            String cat1,
            String cat2,
            String cat3,
            String contentid,
            String contenttypeid,
            String createdtime,
            String firstimage,
            String firstimage2,
            String cpyrhtDivCd,
            String mapx,
            String mapy,
            String mlevel,
            String modifiedtime,
            String sigungucode,
            String tel,
            String title,
            String zipcode,
            String lDongRegnCd,
            String lDongSignguCd,
            String lclsSystm1,
            String lclsSystm2,
            String lclsSystm3
    ) {}
}
