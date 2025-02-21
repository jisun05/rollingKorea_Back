package history.traveler.rollingkorea.comment.controller;

import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentCreateResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentEditResponse;
import history.traveler.rollingkorea.comment.controller.response.CommentSearchResponse;
import history.traveler.rollingkorea.comment.controller.response.ReplySearchResponse;
import history.traveler.rollingkorea.comment.service.CommentService;
import history.traveler.rollingkorea.comment.service.Implementation.CommentServiceImpl;
import history.traveler.rollingkorea.comment.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class CommentController {

    private final CommentService commentService;
    private final CommentServiceImpl commentServiceImpl;
    private ReplyService replyService;

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new comment", description = "Creates a new comment for a specific user.")
    public CommentCreateResponse createComment(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @RequestBody @Valid CommentCreateRequest commentCreateRequest) {
        return commentService.createComment(userId, commentCreateRequest);
    }

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all comments", description = "Fetches all comments with pagination support.")
    public Page<CommentSearchResponse> commentFindAll(
            @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.commentFindAll(pageable);
    }

    @GetMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a comment by Comment ID", description = "Fetches a specific comment by its unique identifier.")
    public CommentSearchResponse getComment(
            @Parameter(description = "The unique identifier of the comment", required = true) @PathVariable Long commentId) {
        return commentService.findById(commentId);
    }

    @PutMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit a comment", description = "Allows a user to edit their own comment.")
    public CommentEditResponse editComment(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @Parameter(description = "The unique identifier of the comment", required = true) @PathVariable("commentId") Long commentId,
            @RequestBody @Valid CommentEditRequest commentEditRequest) {
        return commentService.editComment(userId, commentId, commentEditRequest);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a comment", description = "Allows a user to delete their own comment.")
    public void deleteComment(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @Parameter(description = "The unique identifier of the comment", required = true) @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
    }

    @GetMapping("/comments/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find comments by user ID", description = "Fetches the comments for a specific user.")
    public Page<CommentSearchResponse> findCommentByUser(
            @Parameter(description = "The unique identifier of the user", required = true) @RequestParam Long userId,
            @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.findByUser_UserId(userId, pageable);
    }

    @GetMapping("/comments/replies/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve replies for a comment", description = "Fetches all replies for a specific comment.")
    public List<ReplySearchResponse> getRepliesByCommentId(
            @Parameter(description = "The unique identifier of the comment", required = true) @PathVariable Long commentId,
            @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
      ;
        return commentService.getRepliesByCommentId(commentId);
    }


    @DeleteMapping("/comments/admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Admin Delete multiple comments", description = "Allows an admin to delete multiple comments at once.")
    public void adminDeleteComments(
            @Parameter(description = "The unique identifier of the admin", required = true) @RequestParam Long adminId,
            @Parameter(description = "The unique identifiers of the comments", required = true) @RequestBody List<Long> commentIds) {
        // 관리자 권한 확인
        commentService.adminDeleteComments(adminId, commentIds);
    }

}

