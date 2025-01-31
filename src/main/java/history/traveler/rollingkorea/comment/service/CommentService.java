package history.traveler.rollingkorea.comment.service;


import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface CommentService {

    //create comment
    void commentCreate(CommentCreateRequest commentCreateRequest);

    //search whole comment
    Page<CommentResponse> commentFindAll(Pageable pageable);


    //edit comment
    void commentEdit(Long commentId, CommentEditRequest commentEditRequest);


    //delete comment
    void commentDelete(Long commentId);

    Page<CommentResponse> findByUser(Pageable pageable);
}
