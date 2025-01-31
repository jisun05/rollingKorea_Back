package history.traveler.rollingkorea.comment.repository;

import history.traveler.rollingkorea.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 날짜 기준으로 코멘트 조회
    @Query("select r from Comment r where r.createdAt >= :startDate")
    Page<Comment> findAllByCreatedDateAfter(LocalDateTime startDate, Pageable pageable);

    //아이디 기준으로 코멘트 조회
    @Query("select r from Comment r where r.user.userId = :userId")
    List<Comment> findByUserId(@Param("userId") Long userId);

}
