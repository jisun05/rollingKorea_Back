package history.traveler.rollingkorea.question.repository;

import history.traveler.rollingkorea.question.domain.ContactUs;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
    Page<ContactUs> findByUser(User user, Pageable pageable);

}
