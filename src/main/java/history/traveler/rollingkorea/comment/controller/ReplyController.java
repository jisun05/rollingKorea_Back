package history.traveler.rollingkorea.comment.controller;


import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {


private final ReplyService replyService;


    @GetMapping("/replies/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find replies by user ID", description = "Fetches the replies for a specific user.")
    public ResponseEntity<List<ReplyResponse>> getRepliesByUserId(@RequestParam Long userId, @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReplyResponse> replies = replyService.getRepliesByUserId(userId, pageable);
        return ResponseEntity.ok(replies);
    }

    //make reply
    @PostMapping("/replies")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('USER')")
    public void replyCreate(@RequestParam Long userId, Long commentId, @RequestBody @Valid ReplyCreateRequest request) {
        replyService.replyCreate(userId, commentId, request);
    }

//edit reply
    @PutMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('USER')")
    public void replyEdit(@RequestParam Long userId, @PathVariable("replyId") Long replyId , @RequestBody @Valid ReplyEditRequest request) {
        replyService.replyEdit(userId, replyId, request);
    }

    //delete reply
    @DeleteMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void deleteReply(@RequestParam Long userId, @PathVariable("replyId") Long replyId) {
        replyService.deleteByReplyId(userId, replyId);
    }


}
