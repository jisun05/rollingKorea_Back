package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePlaceRepository extends JpaRepository<LikePlace, Long> {
    Page<LikePlace> findAllByUser_UserId(Long userId, Pageable pageable);

   // Optional<LikePlace> findByIdAndUser(Long likePlaceId, User user);

    Optional<LikePlace> findByPlaceIdAndUser(Long placeId, User user);
    void deleteById(Long likePlaceId);

   List<LikePlace> findByUser_UserId(Long userId);
}
