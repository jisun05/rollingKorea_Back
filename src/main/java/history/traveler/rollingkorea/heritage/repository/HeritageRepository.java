package history.traveler.rollingkorea.heritage.repository;

import history.traveler.rollingkorea.heritage.domain.Heritage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeritageRepository extends JpaRepository<Heritage, Long> {
}
