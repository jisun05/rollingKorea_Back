package history.traveler.rollingkorea.user.repository;

import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    //Optional<User> findByLoginIdAndLoginType(Long loginId, LoginType loginType);
   User findByUserName(String userName);

    Long userId(Long userId);
}
