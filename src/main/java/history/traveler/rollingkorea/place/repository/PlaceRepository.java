package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    //특정 코멘트에 대한 모든 답글 조회
    Page<Place> findByRegion(Pageable pageable, String region); // 지역에 따른 장소 조회 메소드

    Optional<Place> findByPlaceId(Long placeId);

    Optional<Object> findByPlaceName(@NotNull(message = "The placeName is a required field.") String s);

    // latitude, longitude가 동일한 Place 조회
    Optional<Place> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
