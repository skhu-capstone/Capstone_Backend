package com.skhu.skhucapstone.comment.api;

import com.skhu.skhucapstone.comment.api.dto.request.CommentCreateRequest;
import com.skhu.skhucapstone.comment.api.dto.response.CommentResponse;
import com.skhu.skhucapstone.comment.application.CommentService;
import com.skhu.skhucapstone.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.skhu.skhucapstone.common.exception.SuccessCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CommentCreateRequest request
    ) {

        CommentResponse response = commentService.createComment(
                postId,
                userId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId
    ) {
        commentService.deleteComment(commentId, userId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COMMENT_DELETE_SUCCESS, null));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId
    ) {

        List<CommentResponse> response = commentService.getComments(postId);

        return ResponseEntity.ok(response);
    }
}