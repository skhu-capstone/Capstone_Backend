package com.skhu.skhucapstone.likes.api;

import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import com.skhu.skhucapstone.likes.api.dto.response.LikeResponse;
import com.skhu.skhucapstone.likes.application.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Like", description = "게시글 좋아요 API")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/posts/{postId}/likes")
    @Operation(
            summary = "게시글 좋아요",
            description = "로그인한 사용자가 게시글에 좋아요를 누르거나, 이미 좋아요한 게시글이면 좋아요를 취소합니다."
    )
    public ResponseEntity<ApiResponse<LikeResponse>> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        LikeResponse response = likesService.toggleLike(postId, userId);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.LIKE_TOGGLE_SUCCESS, response)
        );
    }
}