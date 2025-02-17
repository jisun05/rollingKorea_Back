package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentEditResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchResponse;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_COMMENT;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;


    //only user can write
    @Override
    public CommentCreateResponse createComment(Long userId, CommentCreateRequest commentCreateRequest) {
        User user = getUser(); // bring user data
        if (user == null) {

            throw new IllegalArgumentException("user is null");
        }
        Comment comment = Comment.createComment(user, commentCreateRequest);

         commentRepository.save(comment);
        return CommentCreateResponse.from(comment);
    }

    //search whole comment
    @Override
    @Transactional(readOnly = true)
    public Page<CommentSearchResponse> commentFindAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        return comments.map(CommentSearchResponse::new);
    }


    //edit comment
    @Override
    public CommentEditResponse editComment(Long userId, Long commentId, CommentEditRequest commentEditRequest) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        //writeCommentUserEqualLoginUserCheck(user, comment);
        comment.editComment(commentEditRequest);
        return CommentEditResponse.from(comment);
    }

    //delete comment
    @Override
    public void deleteComment(Long userId, Long commentId) {
        //User user = getUser();
        Optional<User> user =  userRepository.findByUserId(userId);
        Comment comment = existCommentCheck(commentId);
       // writeCommentUserEqualLoginUserCheck(user, comment);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentSearchResponse> findByUser_UserId(Long userId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByUser_UserId(userId, pageable);

        return comments.map(CommentSearchResponse::new);

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
//    private void writeCommentUserEqualLoginUserCheck(User user, Comment comment) {
//        if (!comment.getUser().getUserId().equals(user.getUserId())) {
//            throw new BusinessException(NOT_MATCH_COMMENT);
//        }
//    }
}