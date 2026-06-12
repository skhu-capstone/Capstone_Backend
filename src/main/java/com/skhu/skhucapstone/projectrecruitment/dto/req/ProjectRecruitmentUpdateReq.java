package com.skhu.skhucapstone.projectrecruitment.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRecruitmentUpdateReq(

        @NotBlank(message = "모집 글 제목은 필수입니다.")
        @Size(max = 100, message = "모집 글 제목은 100자 이하로 입력해주세요.")
        String title,

        String imageUrl,

        @NotBlank(message = "작성자 스택은 필수입니다.")
        @Size(max = 100, message = "작성자 스택은 100자 이하로 입력해주세요.")
        String writerStack,

        @NotBlank(message = "모집 포지션은 필수입니다.")
        @Size(max = 100, message = "모집 포지션은 100자 이하로 입력해주세요.")
        String positions,

        @NotBlank(message = "모집 글 내용은 필수입니다.")
        @Size(max = 3000, message = "모집 글 내용은 3000자 이하로 입력해주세요.")
        String content,

        @NotNull(message = "마감일은 필수입니다.")
        LocalDate deadline
) {
}
