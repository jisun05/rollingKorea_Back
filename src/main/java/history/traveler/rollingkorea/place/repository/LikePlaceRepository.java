package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePlaceRepository extends JpaRepository<LikePlace, Long> {

    /**
     * 특정 유저가 누른 좋아요 목록 (페이징)
     */
    Page<LikePlace> findAllByUser_UserId(Long userId, Pageable pageable);

    /**
     * 좋아요 토글을 위해 contentId & User 로 조회
     */
    Optional<LikePlace> findByPlace_ContentIdAndUser(Long contentId, User user);

    /**
     * 특정 유저의 좋아요 전체 조회
     */
    List<LikePlace> findByUser_UserId(Long userId);
}
