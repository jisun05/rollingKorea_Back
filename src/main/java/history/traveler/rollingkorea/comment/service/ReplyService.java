package history.traveler.rollingkorea.comment.service;

import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;

public interface ReplyService {



    void replyCreate(Long reviewId, ReplyCreateRequest replyCreateRequest);


    void replyEdit(Long replyId, ReplyEditRequest ReplyEditRequest);


    void replyDelete(Long replyId);
}
