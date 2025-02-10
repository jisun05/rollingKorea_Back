package history.traveler.rollingkorea.comment.repository;

import history.traveler.rollingkorea.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //아이디 기준으로 코멘트 조회
    Page<Comment> findByUser_UserId(Long userId, Pageable pageable);



    //코멘트id 기준으로 코멘트 찾기
    @Query("select r from Comment r where r.commentId = :commentId")
    Optional<Comment> findByCommentId(Long commentId);
}
