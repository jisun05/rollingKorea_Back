package history.traveler.rollingkorea.comment.service;
import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    //create comment
    void createComment(Long userId, CommentCreateRequest commentCreateRequest);

    //search whole comment
    Page<CommentResponse> commentFindAll(Pageable pageable);

    //edit comment
    void editComment(Long commentId, CommentEditRequest commentEditRequest);

    //delete comment
    void deleteComment(Long commentId);

    Page<CommentResponse> findByUser_UserId(Long userId, Pageable pageable);

    Comment findById(Long commentId);
}
