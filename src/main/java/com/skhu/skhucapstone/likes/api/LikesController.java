package com.skhu.skhucapstone.likes.api;

import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import com.skhu.skhucapstone.likes.api.dto.response.LikeResponse;
import com.skhu.skhucapstone.likes.application.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/posts/{postId}/likes")
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