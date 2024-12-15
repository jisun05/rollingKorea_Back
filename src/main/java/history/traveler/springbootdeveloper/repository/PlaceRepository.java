package history.traveler.springbootdeveloper.repository;

import history.traveler.springbootdeveloper.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByRegion(String region); // 지역에 따른 장소 조회 메소드
}
