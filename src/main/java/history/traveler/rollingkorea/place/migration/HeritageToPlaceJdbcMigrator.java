package history.traveler.rollingkorea.place.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeritageToPlaceJdbcMigrator implements ApplicationRunner {

    private final JdbcTemplate jdbc;

    @Override
    public void run(ApplicationArguments args) {

        // 1) 기존 데이터 삭제
        jdbc.update("DELETE FROM place");


        String sql = """
            INSERT INTO place (
              content_id,
              title,
              addr1,
              addr2,
              area_code,
              cat1,
              cat2,
              cat3,
              content_type_id,
              created_time,
              first_image,
              first_image2,
              copyright_div_cd,
              map_x,
              map_y,
              m_level,
              modified_time,
              sigungu_code,
              tel,
              zipcode,
              l_dong_regn_cd,
              l_dong_signgu_cd,
              lcls_systm1,
              lcls_systm2,
              lcls_systm3,
              imported_at
            )
            SELECT
              h.contentId,
              h.title,
              h.addr1,
              h.addr2,
              h.areaCode,
              h.cat1,
              h.cat2,
              h.cat3,
              h.contentTypeId,
              h.createdTime,
              h.firstImage,
              h.firstImage2,
              h.copyrightDivCd,
              h.mapX,
              h.mapY,
              h.mLevel,
              h.modifiedTime,
              h.sigunguCode,
              h.tel,
              h.zipcode,
              h.lDongRegnCd,
              h.lDongSignguCd,
              h.lclsSystm1,
              h.lclsSystm2,
              h.lclsSystm3,
              NOW()
            FROM heritage h
            WHERE NOT EXISTS (
              SELECT 1 FROM place p
              WHERE p.content_id = h.contentId
            )
        """;

        int inserted = jdbc.update(sql);
        log.info("✅ Heritage → Place 마이그레이션 삽입 완료");
    }
}
