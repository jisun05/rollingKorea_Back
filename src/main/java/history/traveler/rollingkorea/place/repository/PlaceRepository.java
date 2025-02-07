package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    //특정 코멘트에 대한 모든 답글 조회

    List<Place> findByRegion(String region); // 지역에 따른 장소 조회 메소드

    @Override
    Page<Place> findAll(Pageable pageable);

    Optional<Place> findByPlaceId(Long placeId);



}
