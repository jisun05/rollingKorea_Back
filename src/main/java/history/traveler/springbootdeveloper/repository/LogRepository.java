package history.traveler.springbootdeveloper.repository;

import history.traveler.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Article, Long> {
}
