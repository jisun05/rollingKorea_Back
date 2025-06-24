package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentEditResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplySearchResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.comment.repository.CommentRepository;
import history.traveler.rollingkorea.comment.repository.ReplyRepository;
import history.traveler.rollingkorea.comment.service.CommentService;
import history.traveler.rollingkorea.global.error.ErrorCode;
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

import java.util.List;
import java.util.Optional;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_COMMENT;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    @Override
    public CommentCreateResponse createComment(Long userId, CommentCreateRequest commentCreateRequest) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        Comment comment = Comment.createComment(user, commentCreateRequest);
        commentRepository.save(comment);

        return CommentCreateResponse.from(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentSearchResponse> commentFindAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(CommentSearchResponse::new);
    }

    @Override
    public CommentEditResponse editComment(Long userId, Long commentId, CommentEditRequest commentEditRequest) {
        User user = getUser();
        Comment comment = existCommentCheck(commentId);
        // writeCommentUserEqualLoginUserCheck(user, comment);
        comment.updateContent(commentEditRequest.content()); // DTO의 content 필드만 꺼내서 전달
        return CommentEditResponse.from(comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Comment comment = existCommentCheck(commentId);
        // writeCommentUserEqualLoginUserCheck(user.get(), comment);
        commentRepository.delete(comment);
    }

    @Override
    public void adminDeleteComments(Long adminId, List<Long> commentIds) {
        List<Comment> comments = commentRepository.findAllById(commentIds);
        if (comments.size() != commentIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_REPLY);
        }
        commentRepository.deleteAll(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentSearchResponse> findByUser_UserId(Long userId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByUser_UserId(userId, pageable);
        return comments.map(CommentSearchResponse::new);
    }

    @Override
    public CommentSearchResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        return new CommentSearchResponse(comment);
    }

    @Override
    public List<ReplySearchResponse> getRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findByCommentId(commentId);
        return ReplySearchResponse.fromList(replies);
    }


    @Transactional
    @Override
    public void patchCommentContent(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        comment.updateContent(content); // <- 이 메서드는 Comment 엔티티에 정의되어 있어야 함
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName();
            return userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
        }
        throw new BusinessException(NOT_FOUND_USER);
    }

    private Comment existCommentCheck(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

}
