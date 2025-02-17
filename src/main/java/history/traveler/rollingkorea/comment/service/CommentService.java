package history.traveler.rollingkorea.comment.service;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentEditResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CommentService {

    //create comment
    CommentCreateResponse createComment(Long userId, CommentCreateRequest commentCreateRequest);

    //search whole comment
    Page<CommentSearchResponse> commentFindAll(Pageable pageable);

    //edit comment
    CommentEditResponse editComment(Long userId, Long commentId, CommentEditRequest commentEditRequest);

    //delete comment
    //void deleteComment(Long commentId);

    //delete comment
    void deleteComment(Long userId, Long commentId);

    Page<CommentSearchResponse> findByUser_UserId(Long userId, Pageable pageable);

    Comment findById(Long commentId);

    List<ReplyResponse> getRepliesByCommentId(Long commentId);
}
