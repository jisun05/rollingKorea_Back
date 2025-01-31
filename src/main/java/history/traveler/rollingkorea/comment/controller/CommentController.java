package history.traveler.rollingkorea.comment.controller;


import history.traveler.rollingkorea.comment.controller.request.CommentCreateRequest;
import history.traveler.rollingkorea.comment.controller.request.CommentEditRequest;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    //create comment
    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void commentCreate(Long userId, @RequestBody @Valid CommentCreateRequest commentCreateRequest) {
         commentService.commentCreate(commentCreateRequest);
    }

    //comment search
    @GetMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentResponse> commentFindAll( @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.commentFindAll(pageable); // 댓글 서비스에서 페이지 정보를 기반으로 모든 댓글을 조회하여 반환
    }

    //edit comment
    @PutMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public void commentEdit(@PathVariable("commentId") Long commentId, @RequestBody @Valid CommentEditRequest commentEditRequest) {
        commentService.commentEdit(commentId, commentEditRequest);
    }

    //delete comment
    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void commentDelete(@PathVariable("commentId") Long commentId) {
        commentService.commentDelete(commentId);
    }

    //search by id
    @GetMapping("/comment/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentResponse> findByUser( @PageableDefault(sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.findByUser(pageable);
    }



}
