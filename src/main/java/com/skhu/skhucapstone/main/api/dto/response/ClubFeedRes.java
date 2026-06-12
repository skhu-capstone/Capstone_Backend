package com.skhu.skhucapstone.main.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ClubFeedRes {

    private Long postId;

    private String writerName;

    private String clubName;

    private LocalDateTime createdAt;

    private List<String> imageUrls;

    private String content;
}