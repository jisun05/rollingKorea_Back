package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * contentId 기준으로 이미지 조회
     */
    List<Image> findByPlace_ContentId(Long contentId);

    /**
     * contentId 기준으로 이미지 삭제
     */
    void deleteByPlace_ContentId(Long contentId);
}
