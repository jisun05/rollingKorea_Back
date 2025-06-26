package history.traveler.rollingkorea.heritage.service;

import history.traveler.rollingkorea.heritage.domain.Heritage;

import java.util.List;

public interface HeritageService {
    void fetchAndSaveHeritagesFromTourAPI() throws Exception;

    List<Heritage> getAllFromDatabase();
}
