package history.traveler.rollingkorea.question.repository;

import history.traveler.rollingkorea.question.domain.ContactUs;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
    Page<ContactUs> findByUser(User user, Pageable pageable);

    List<ContactUs> findByParentId(Long parentId);


    // 특정 parentId를 가진 답글들 중 최대 listOrder 값을 조회
    @Query("SELECT COALESCE(MAX(c.listOrder), 0) FROM ContactUs c WHERE c.parentId = :parentId")
    Long findMaxListOrderByParentId(@Param("parentId") Long parentId);

}
