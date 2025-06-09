package history.traveler.rollingkorea.comment.controller;


import history.traveler.rollingkorea.comment.controller.request.ReplyCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.ReplyEditRequest;
import history.traveler.rollingkorea.comment.controller.response.ReplyCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyEditResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplySearchResponse;
import history.traveler.rollingkorea.comment.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/replies/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find replies by user ID", description = "Fetches the replies for a specific user.")
    public List<ReplySearchResponse> getRepliesByUserId(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @PageableDefault(sort = "commentId", direction = Sort.Direction.DESC) Pageable pageable) {
        return replyService.getRepliesByUserId(userId, pageable);

    }

    @PostMapping("/replies")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a reply", description = "Allows a user to create a reply on a comment.")
    public ReplyCreateResponse replyCreate(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @Parameter(description = "The unique identifier of the comment", required = true) @RequestParam Long commentId,
            @RequestBody @Valid ReplyCreateRequest request) {
        return replyService.replyCreate(userId, commentId, request);
    }

    @PutMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit a reply", description = "Allows a user to edit their own reply.")
    public ReplyEditResponse replyEdit(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @Parameter(description = "The unique identifier of the reply", required = true) @PathVariable("replyId") Long replyId,
            @RequestBody @Valid ReplyEditRequest request) {
        return replyService.replyEdit(userId, replyId, request);
    }

    @DeleteMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a reply", description = "Allows a user to delete their own reply.")
    public void deleteReply(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @Parameter(description = "The unique identifier of the reply", required = true) @PathVariable("replyId") Long replyId) {
        replyService.deleteByReplyId(userId, replyId);
    }

    @DeleteMapping("/replies/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Admin Delete multiple replies", description = "Allows an admin to delete multiple replies at once.")
    public void adminDeleteReplies(
            @Parameter(description = "The unique identifier of the admin", required = true) @RequestParam Long adminId,
            @Parameter(description = "The unique identifiers of the replies", required = true) @RequestBody List<Long> replyIds) {
        // 관리자 권한 확인
        replyService.adminDeleteReplies(adminId, replyIds);
    }


}
