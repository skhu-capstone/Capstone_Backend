package com.skhu.skhucapstone.post.api.dto.request;

import com.skhu.skhucapstone.post.domain.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(

        @NotBlank
        String title,

        @NotBlank
        String content,

        String imageUrl,

        @NotNull
        PostType postType
) {
}