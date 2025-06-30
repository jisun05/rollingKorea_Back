package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    /**
     * heritage 기준 areaCode 로 장소 조회
     */
    Page<Place> findByAreaCode(Pageable pageable, Integer areaCode);

    /**
     * contentId 로 단일 장소 조회
     */
    Optional<Place> findByContentId(Long contentId);

    /**
     * contentId 중복 체크
     */
    boolean existsByContentId(Long contentId);

    /**
     * contentId 기준 장소 삭제
     */
    void deleteByContentId(Long contentId);
}
