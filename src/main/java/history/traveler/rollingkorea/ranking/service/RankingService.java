package history.traveler.rollingkorea.ranking.service;

import history.traveler.rollingkorea.ranking.controller.response.RankingResponse;
import history.traveler.rollingkorea.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;

    public List<RankingResponse> getRankingByLikes() {
        return rankingRepository.findAllByOrderByCountLikeDesc()
                .stream()
                .map(r -> new RankingResponse(
                        r.getRankingId(),
                        r.getPlaceId(),
                        r.getPosition(),
                        r.getCountLike(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
