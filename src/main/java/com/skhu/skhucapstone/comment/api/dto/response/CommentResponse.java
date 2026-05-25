package com.skhu.skhucapstone.comment.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;

    private String content;

    private String writerName;

    private LocalDateTime createdAt;
}