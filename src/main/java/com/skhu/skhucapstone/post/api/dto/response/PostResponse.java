package com.skhu.skhucapstone.post.api.dto.response;

import com.skhu.skhucapstone.post.domain.PostType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private List<String> imageUrls;

    private PostType postType;

    private String writerName;

    private LocalDateTime createdAt;
}