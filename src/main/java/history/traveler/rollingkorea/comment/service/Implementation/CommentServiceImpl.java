package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.repository.CommentRepository;
import history.traveler.rollingkorea.comment.service.CommentService;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static history.traveler.rollingkorea.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;



    //only user can write
    @Override
    public void commentCreate(CommentCreateRequest commentCreateRequest) {
        User user = getUser(); // bring user data
        if(user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Comment comment = Comment.createComment(user, commentCreateRequest);
        commentRepository.save(comment);
    }

    //search whole comment
    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> commentFindAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        return comments.map(CommentResponse::new);
    }


    //edit comment
    @Override
    public void commentEdit(Long commentId, CommentEditRequest commentEditRequest) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        writeCommentUserEqualLoginUserCheck(user, comment);
        comment.editComment(commentEditRequest);

    }

    //delete comment
    @Override
    public void commentDelete(Long commentId) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        writeCommentUserEqualLoginUserCheck(user, comment);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> findByUser(Pageable pageable) {
        User user = getUser();
        Page<Comment> comments = commentRepository.findByUser(user, pageable);
        return comments.map(CommentResponse::new);
    }





    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName(); // bring loginId

            return userRepository.findByLoginId(loginId) //search by loginId
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
        }

        return null;
    }

    //check if the comment is exist
    private Comment existCommentCheck(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

    //check comment's owner
    private void writeCommentUserEqualLoginUserCheck(User user, Comment comment) {
        if(!comment.getUser().getLoginId().equals(user.getLoginId())) {
            throw new BusinessException(NOT_MATCH_COMMENT);
        }
    }



}
