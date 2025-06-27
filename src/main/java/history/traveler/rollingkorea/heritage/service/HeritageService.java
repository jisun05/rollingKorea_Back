package history.traveler.rollingkorea.heritage.service;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.response.HeritageResponse;
import java.util.List;

public interface HeritageService {
    // 1) 외부 TourAPI에서 가져와서 DB에 저장만 한다 (명령)
    void fetchAndSaveHeritagesFromTourAPI() throws Exception;

    // 2) DB에 저장된 엔티티 목록을 리턴한다 (조회)
    List<Heritage> getAllFromDatabase();
}

