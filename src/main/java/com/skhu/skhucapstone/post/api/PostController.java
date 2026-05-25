package com.skhu.skhucapstone.post.api;

import com.skhu.skhucapstone.post.api.dto.request.PostCreateRequest;
import com.skhu.skhucapstone.post.api.dto.response.PostResponse;
import com.skhu.skhucapstone.post.application.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 API")
public class PostController {

    private final PostService postService;

    @PostMapping("/clubs/{clubId}/posts")
    @Operation(summary = "게시글 생성", description = "STAFF 또는 PRESIDENT 권한을 가진 동아리 멤버가 게시글을 작성합니다.")
    public ResponseEntity<PostResponse> createPost(
            @PathVariable Long clubId,
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PostCreateRequest request
    ) {
        PostResponse response = postService.createPost(clubId, userId, request);
        return ResponseEntity.ok(response);
    }
}