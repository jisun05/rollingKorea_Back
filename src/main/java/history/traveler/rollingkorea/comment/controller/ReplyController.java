package history.traveler.rollingkorea.comment.controller;


import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {


private final ReplyService replyService;


    @GetMapping("/reply/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReplyResponse>> getRepliesByUserId(@PathVariable Long userId, @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReplyResponse> replies = replyService.getRepliesByUserId(userId, pageable);
        return ResponseEntity.ok(replies);
    }

    //make reply
    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void replyCreate(Long commentId, @RequestBody @Valid ReplyCreateRequest request) {
        replyService.replyCreate(commentId, request);
    }

//edit reply
    @PutMapping("/reply/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public void replyEdit(@PathVariable("replyId") Long replyId , @RequestBody @Valid ReplyEditRequest request) {
        replyService.replyEdit(replyId, request);
    }

    //delete reply
    @DeleteMapping("/reply/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void deleteReply(@PathVariable("replyId") Long replyId) {
        replyService.deleteByReplyId(replyId);
    }









}
