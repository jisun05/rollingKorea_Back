package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.domain.Reply;
import history.traveler.rollingkorea.comment.repository.CommentRepository;
import history.traveler.rollingkorea.comment.repository.ReplyRepository;
import history.traveler.rollingkorea.comment.service.ReplyService;
import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_MATCH_REPLY;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Override
    public void replyCreate(Long userId, Long commentId, ReplyCreateRequest replyCreateRequest) {
        User user = getUser();

        //find comment
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        Reply reply = Reply.createReply(user, comment, replyCreateRequest);
        replyRepository.save(reply);
    }

    //edit reply
    @Override
    public void replyEdit(Long userId, Long replyId, ReplyEditRequest replyEditRequest) {
        User user = getUser();
        Reply reply = existMemberWriteReplyCheck(replyId, user);
        reply.edit(replyEditRequest.content());
    }

    @Override
    public void deleteByReplyId(Long userId, Long replyId) {
        User user = getUser();
        existMemberWriteReplyCheck(replyId, user);
        replyRepository.deleteByReplyId(replyId);
    }


    @Override
    public List<ReplyResponse> getRepliesByUserId(Long userId, Pageable pageable) {
        List<Reply> replies = replyRepository.findByUserId(userId);
        return replies.stream()
                .map(ReplyResponse::new)
                .collect(Collectors.toList());
    }

//테스트를 위한 주석
//    private User getUser() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByLoginId(authentication.getName())
//                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
//    }

    private User getUser() {
        // 🔥 테스트용 더미 유저 추가 (로그인 없이 Swagger 테스트 가능)
        return User.builder()
                .userId(1L)
                .loginId("jisunnala@gmail.com")
                .nickname("TestUser")
                .build();
    }

    //check user's reply
    private Reply existMemberWriteReplyCheck(Long replyId, User user) {
        return replyRepository.findByReplyIdAndUser_UserId(replyId, user.getUserId())
                .orElseThrow(() -> new BusinessException(NOT_MATCH_REPLY));
    }



}
