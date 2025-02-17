package history.traveler.rollingkorea.comment.service;

import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {



    void replyCreate(Long userId, Long reviewId, ReplyCreateRequest replyCreateRequest);


    void replyEdit(Long userId,Long replyId, ReplyEditRequest ReplyEditRequest);


    void deleteByReplyId(Long userId, Long replyId);



    List<ReplyResponse> getRepliesByUserId(Long userId, Pageable pageable);
}
