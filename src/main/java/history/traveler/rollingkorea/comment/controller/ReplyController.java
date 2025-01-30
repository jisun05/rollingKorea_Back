package history.traveler.rollingkorea.comment.controller;



import history.traveler.rollingkorea.comment.service.ReplyService;
import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {


private final ReplyService replyService;



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
    @DeleteMapping("/reply/{reply}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void replyDelete(@PathVariable("replyId") Long replyId) {
        replyService.replyDelete(replyId);
    }









}
