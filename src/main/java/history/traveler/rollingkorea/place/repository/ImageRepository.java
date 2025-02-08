package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ImageRepository extends JpaRepository<Image, Long> {


    // placeId 기준으로 imagePath 값 전부 가져오기

    @Query("SELECT i FROM Image i WHERE i.place.placeId = :placeId")
    List<Image> findByPlace_PlaceId(Long placeId);

    void deleteByPlace_PlaceId(Long placeId);
}
