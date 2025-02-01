package history.traveler.rollingkorea.comment.service;


import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    //create comment
    void createComment(CommentCreateRequest commentCreateRequest);

    //search whole comment
    Page<CommentResponse> commentFindAll(Pageable pageable);


    //edit comment
    void editComment(Long commentId, CommentEditRequest commentEditRequest);


    //delete comment
    void deleteComment(Long commentId);

    Page<CommentResponse> findByUser(Pageable pageable);
}
