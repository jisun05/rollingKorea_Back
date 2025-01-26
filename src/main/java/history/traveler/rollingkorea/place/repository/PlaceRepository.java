package history.traveler.rollingkorea.place.repository;

import history.traveler.rollingkorea.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA의 JpaRepository 임포트, 이 인터페이스는 CRUD 작업을 위한 기본 메소드를 제공
/// Spring의 @Repository 어노테이션 임포트,이 어노테이션은 해당 인터페이스가 데이터 접근 레이어임을 나타냄
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByRegion(String region); // 지역에 따른 장소 조회 메소드
}
