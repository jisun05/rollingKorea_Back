package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ImageRepository extends JpaRepository<Image, Long> {


    // placeId 기준으로 imagePath 값 전부 가져오기
    List<Image> findByPlace_PlaceId(Long placeId);

}
