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

import java.util.List;

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

    @GetMapping("/posts")
    @Operation(summary = "전체 게시글 조회", description = "전체 동아리 게시글을 최신순으로 조회합니다.")
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<PostResponse> response = postService.getPosts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clubs/{clubId}/posts")
    @Operation(summary = "동아리별 게시글 조회", description = "특정 동아리의 게시글을 최신순으로 조회합니다.")
    public ResponseEntity<List<PostResponse>> getClubPosts(
            @PathVariable Long clubId
    ) {
        List<PostResponse> response = postService.getClubPosts(clubId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }
}