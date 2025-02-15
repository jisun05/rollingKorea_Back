package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchAllResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.comment.repository.CommentRepository;
import history.traveler.rollingkorea.comment.repository.ReplyRepository;
import history.traveler.rollingkorea.comment.service.CommentService;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_COMMENT;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_MATCH_COMMENT;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;


    //only user can write
    @Override
    public void createComment(Long userId, CommentCreateRequest commentCreateRequest) {
        User user = getUser(); // bring user data
        if (user == null) {
            System.out.println("check test111");
            throw new IllegalArgumentException("user is null");
        }
      Comment comment = Comment.createComment(user, commentCreateRequest);
        System.out.println("DEBUG: comment userId = " + comment.getUser().getUserId());
        commentRepository.save(comment);
    }

    //search whole comment
    @Override
    @Transactional(readOnly = true)
    public Page<CommentSearchAllResponse> commentFindAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        return comments.map(CommentSearchAllResponse::new);
    }


    //edit comment
    @Override
    public void editComment(Long commentId, CommentEditRequest commentEditRequest) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        writeCommentUserEqualLoginUserCheck(user, comment);
        comment.editComment(commentEditRequest);

    }

    //delete comment
    @Override
    public void deleteComment(Long commentId) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        writeCommentUserEqualLoginUserCheck(user, comment);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> findByUser_UserId(Long userId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByUser_UserId(userId, pageable);
        return comments.map(CommentResponse::new);
    }

    @Override
    public Comment findById(Long commentId) {
        //TODO :
        return null;
    }

    @Override
    public List<ReplyResponse> getRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findByCommentId(commentId);
        return replies.stream()
                .map(ReplyResponse::new)
                .collect(Collectors.toList());
    }



    public User getCommentForUser(Long userId) {
        return userRepository.findById(userId).get();
    }
// 원래 코드
//
//    private User getUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String loginId = authentication.getName(); // bring loginId
//
//            return userRepository.findByLoginId(loginId) //search by loginId
//                    .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
//        }
//
//        return null;
//    }

    private User getUser() {
        // 🔥 테스트용 더미 유저 추가 (로그인 없이 Swagger 테스트 가능)
        return User.builder()
                .userId(1L)
                .loginId("jisunnala@gmail.com")
                .nickname("TestUser")
                .build();
    }


    //check if the comment is exist
    private Comment existCommentCheck(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

    //check comment's owner
    private void writeCommentUserEqualLoginUserCheck(User user, Comment comment) {
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new BusinessException(NOT_MATCH_COMMENT);
        }
    }
}