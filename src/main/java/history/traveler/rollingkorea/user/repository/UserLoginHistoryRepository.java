package history.traveler.rollingkorea.user.repository;

import history.traveler.rollingkorea.user.domain.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
}
