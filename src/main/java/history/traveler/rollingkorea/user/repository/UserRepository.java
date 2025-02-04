package history.traveler.rollingkorea.user.repository;

import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByLoginIdAndRequestLoginId(String loginId, String requestLoginId);
   User findByUserName(String userName);

}
