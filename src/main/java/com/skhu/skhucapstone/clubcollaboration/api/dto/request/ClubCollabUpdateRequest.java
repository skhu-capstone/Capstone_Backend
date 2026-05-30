package com.skhu.skhucapstone.clubcollaboration.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClubCollabUpdateRequest(

        @NotBlank(message = "협업 모집글 제목은 필수입니다.")
        @Size(max = 100, message = "협업 모집글 제목은 100자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "대회명은 필수입니다.")
        @Size(max = 100, message = "대회명은 100자 이하로 입력해주세요.")
        String contestName,

        @NotNull(message = "대회 날짜는 필수입니다.")
        LocalDate contestDate,

        @NotBlank(message = "협업 모집글 내용은 필수입니다.")
        @Size(max = 3000, message = "협업 모집글 내용은 3000자 이하로 입력해주세요.")
        String content,

        @NotNull(message = "마감일은 필수입니다.")
        LocalDate deadline
) {
}