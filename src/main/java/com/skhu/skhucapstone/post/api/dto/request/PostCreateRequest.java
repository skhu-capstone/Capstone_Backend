package com.skhu.skhucapstone.post.api.dto.request;

import com.skhu.skhucapstone.post.domain.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PostCreateRequest(

        @NotBlank
        String title,

        @NotBlank
        String content,

        List<String> imageUrls,

        @NotNull
        PostType postType
) {
}