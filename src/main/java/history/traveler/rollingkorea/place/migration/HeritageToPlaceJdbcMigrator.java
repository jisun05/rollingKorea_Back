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

        // (1) image
        jdbc.update("DELETE FROM image");
        // 2)  place
        jdbc.update("DELETE FROM place");

        // 2) heritage → place 마이그레이션 (snake_case)
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
              h.content_id,
              h.title,
              h.addr1,
              h.addr2,
              h.area_code,
              h.cat1,
              h.cat2,
              h.cat3,
              h.content_type_id,
              h.created_time,
              h.copyright_div_cd,
              h.map_x,
              h.map_y,
              h.m_level,
              h.modified_time,
              h.sigungu_code,
              h.tel,
              h.zipcode,
              h.l_dong_regn_cd,
              h.l_dong_signgu_cd,
              h.lcls_systm1,
              h.lcls_systm2,
              h.lcls_systm3,
              NOW()
            FROM heritage AS h
        """;

        int inserted = jdbc.update(sql);
        log.info("✅ Heritage → Place 마이그레이션: 총 {}건 삽입 완료", inserted);
    }
}


