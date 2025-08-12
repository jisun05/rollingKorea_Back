package history.traveler.rollingkorea.ranking.repository;

import history.traveler.rollingkorea.ranking.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findAllByOrderByCountLikeDesc();
}
