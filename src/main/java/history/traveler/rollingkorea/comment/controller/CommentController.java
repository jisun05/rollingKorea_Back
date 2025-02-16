package history.traveler.rollingkorea.comment.controller;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchAllResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplyResponse;
import history.traveler.rollingkorea.comment.domain.Comment;
import history.traveler.rollingkorea.comment.service.CommentService;
import history.traveler.rollingkorea.comment.service.Implementation.CommentServiceImpl;
import history.traveler.rollingkorea.comment.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final CommentServiceImpl commentServiceImpl;
    private ReplyService replyService;

    //create comment
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Create comment by user.", description = "Create the comment for a specific user.")
    public void createComment(Long userId, @RequestBody @Valid CommentCreateRequest commentCreateRequest) {
         commentService.createComment(userId, commentCreateRequest);
    }

    //comment search
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentSearchAllResponse> commentFindAll( @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.commentFindAll(pageable); // 댓글 서비스에서 페이지 정보를 기반으로 모든 댓글을 조회하여 반환
    }

    //comment search
    @GetMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId);
        CommentResponse commentResponse = new CommentResponse(comment);

        return ResponseEntity.ok(commentResponse);
    }

    //edit comment
    @PutMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public void editComment(@PathVariable("commentId") Long commentId, @RequestBody @Valid CommentEditRequest commentEditRequest) {
        commentService.editComment(commentId, commentEditRequest);
    }

    //delete comment
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/comments/user")
    @Operation(summary = "Find comments by user ID", description = "Fetches the comments for a specific user.")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentResponse> findCommentByUser(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.findByUser_UserId(userId, pageable);
    }


    @GetMapping("/comments/replies/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReplyResponse>> getRepliesByCommentId(@PathVariable Long commentId, @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReplyResponse> replies = commentService.getRepliesByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }

}
