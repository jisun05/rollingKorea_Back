package history.traveler.rollingkorea.comment.service.Implementation;

import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyEditResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplySearchResponse;
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

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_MATCH_REPLY;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Override
    public ReplyCreateResponse replyCreate(Long userId, Long commentId, ReplyCreateRequest replyCreateRequest) {
        User user = getUser();

        //find comment
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        Reply reply = Reply.createReply(user, comment, replyCreateRequest);
        replyRepository.save(reply);

        return new ReplyCreateResponse(reply);
    }

    //edit reply
    @Override
    public ReplyEditResponse replyEdit(Long userId, Long replyId, ReplyEditRequest replyEditRequest) {
        User user = getUser();

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REPLY));

        // 3. 수정된 내용으로 대댓글 업데이트
        reply.edit(replyEditRequest.content());

        // 4. 대댓글 수정 결과를 저장
        replyRepository.save(reply);

        // 5. 수정된 대댓글 정보로 ReplyEditResponse 반환
        return new ReplyEditResponse(reply);
    }

    @Override
    public void deleteByReplyId(Long userId, Long replyId) {
        User user = getUser();
        existMemberWriteReplyCheck(replyId, user);
        replyRepository.deleteByReplyId(replyId);
    }

    @Override
    public void adminDeleteReplies(Long adminId, List<Long> replyIds) {
        // 여러 개의 replyId에 대해 삭제 작업 처리
        List<Reply> replies = replyRepository.findAllById(replyIds);

        // 해당하는 reply가 없으면 예외 처리
        if (replies.size() != replyIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_REPLY);
        }

        // 해당하는 replies를 삭제
        replyRepository.deleteAll(replies);
    }



    @Override
    public List<ReplySearchResponse> getRepliesByUserId(Long userId, Pageable pageable) {
        // 1. 주어진 userId에 해당하는 대댓글 목록 조회 (페이징 처리)
        List<Reply> replies = replyRepository.findByUserId(userId, pageable);

        // 2. 조회된 대댓글 목록을 ReplySearchResponse로 변환하여 반환
        return ReplySearchResponse.fromList(replies);

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
