package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePlaceRepository extends JpaRepository<LikePlace, Long> {

    Page<LikePlace> findAllByUser(User user, Pageable pageable);

   

    //Optional<PlaceResponse> findByUser_UserId(Long userId);

    void deleteById(Long likePlaceId);


    Optional<LikePlace> findByPlace_PlaceIdAndUser(Long placeId, User user);
}
