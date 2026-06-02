package com.skhu.skhucapstone.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(

        @NotBlank
        String content
) {
}