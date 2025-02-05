package history.traveler.rollingkorea.comment.repository;
import history.traveler.rollingkorea.comment.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //특정 코멘트에 대한 모든 답글 조회
    @Query("select r from Reply r where r.comment.commentId = :commentId")
    List<Reply> findByCommentId(@Param("commentId") Long commentId);

    Optional<Reply> findByReplyIdAndUser_UserId(Long replyId, Long userId);





}

