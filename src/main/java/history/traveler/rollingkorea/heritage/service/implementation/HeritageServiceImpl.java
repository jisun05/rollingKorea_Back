package history.traveler.rollingkorea.heritage.service.impl;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.dto.request.HeritageRequest;
import history.traveler.rollingkorea.heritage.dto.response.HeritageResponse;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import history.traveler.rollingkorea.heritage.service.HeritageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeritageServiceImpl implements HeritageService {

    private final RestTemplate restTemplate;
    private final HeritageRepository heritageRepository;

    @Value("${heritage.api.key}")
    private String serviceKey;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    @Transactional
    public void fetchAndSaveHeritagesFromTourAPI() throws Exception {
        int pageNo = 1, pageSize = 100;
        while (true) {
            HeritageRequest req = new HeritageRequest(
                    serviceKey, "ETC", "AppTest", "json", "C", 76, pageSize, pageNo
            );

            HeritageResponse resp = restTemplate.getForObject(
                    req.toUri(), HeritageResponse.class
            );

            List<HeritageResponse.HeritageItem> items =
                    resp.wrapper().body().items().itemList();

            if (items.isEmpty()) break;

            List<Heritage> entities = items.stream()
                    .map(this::toEntity)
                    .toList();

            heritageRepository.saveAll(entities);

            // 마지막 페이지면 종료
            if (pageNo * pageSize >= resp.wrapper().body().totalCount()) {
                break;
            }
            pageNo++;
        }
    }

    private Heritage toEntity(HeritageResponse.HeritageItem it) {
        return new Heritage(
                Long.parseLong(it.contentid()),
                it.title(),
                it.addr1(),
                it.addr2(),
                Integer.valueOf(it.areacode()),
                it.cat1(),
                it.cat2(),
                it.cat3(),
                Integer.valueOf(it.contenttypeid()),
                LocalDateTime.parse(it.createdtime(), DTF),
                it.firstimage(),
                it.firstimage2(),
                it.cpyrhtDivCd(),
                Double.valueOf(it.mapx()),
                Double.valueOf(it.mapy()),
                Integer.valueOf(it.mlevel()),
                LocalDateTime.parse(it.modifiedtime(), DTF),
                Integer.valueOf(it.sigungucode()),
                it.tel(),
                it.zipcode(),
                Integer.valueOf(it.lDongRegnCd()),
                Integer.valueOf(it.lDongSignguCd()),
                it.lclsSystm1(),
                it.lclsSystm2(),
                it.lclsSystm3()
        );
    }

    @Override
    public List<Heritage> getAllFromDatabase() {
        return heritageRepository.findAll();
    }
}
