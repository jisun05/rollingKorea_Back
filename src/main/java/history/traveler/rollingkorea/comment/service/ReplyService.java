package history.traveler.rollingkorea.comment.service;

import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyEditResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplySearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {



    ReplyCreateResponse replyCreate(Long userId, Long reviewId, ReplyCreateRequest replyCreateRequest);


    ReplyEditResponse replyEdit(Long userId, Long replyId, ReplyEditRequest ReplyEditRequest);


    void deleteByReplyId(Long userId, Long replyId);



    List<ReplySearchResponse> getRepliesByUserId(Long userId, Pageable pageable);

    void adminDeleteReplies(Long adminId, List<Long> replyIds);
}
