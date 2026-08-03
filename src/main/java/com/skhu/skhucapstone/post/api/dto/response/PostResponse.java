package com.skhu.skhucapstone.post.api.dto.response;

import com.skhu.skhucapstone.post.domain.PostType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponse {

    private String clubName;

    private Long postId;

    private String title;

    private String content;

    private List<String> imageUrls;

    private PostType postType;

    private String writerName;

    private long likeCount;

    private boolean liked;

    private LocalDateTime createdAt;
}