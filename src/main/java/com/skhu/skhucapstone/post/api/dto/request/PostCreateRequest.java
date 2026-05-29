package com.skhu.skhucapstone.post.api.dto.request;

import com.skhu.skhucapstone.post.domain.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostCreateRequest(

        @NotBlank(message = "게시글 제목은 필수입니다.")
        @Size(max = 100, message = "게시글 제목은 100자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "게시글 내용은 필수입니다.")
        @Size(max = 3000, message = "게시글 내용은 3000자 이하로 입력해주세요.")
        String content,

        List<String> imageUrls,

        @NotNull(message = "게시글 타입은 필수입니다.")
        PostType postType
) {
}