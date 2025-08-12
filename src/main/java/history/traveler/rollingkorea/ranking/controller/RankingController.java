package history.traveler.rollingkorea.ranking.controller;

import history.traveler.rollingkorea.ranking.domain.Ranking;
import history.traveler.rollingkorea.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/by-likes")
    public List<RankingResponse> getRankingsByLikes() {
        return rankingService.getRankingByLikes();
    }
}
