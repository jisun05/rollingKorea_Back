package history.traveler.rollingkorea.comment.repository;

import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 날짜 기준으로 코멘트 조회
    @Query("select r from Comment r where r.createdAt >= :startDate")
    Page<Comment> findAllByCreatedDateAfter(LocalDateTime startDate, Pageable pageable);

    //아이디 기준으로 코멘트 조회
    @Query("select r from Comment r where r.user.userId = :userId")
    Page<Comment> findByUser(User user, Pageable pageable);

    
    //코멘트id 기준으로 코멘트 찾기
    @Query("select r from Comment r where r.commentId = :commentId")
    Optional<Comment> findByCommentId(Long commentId);
}
