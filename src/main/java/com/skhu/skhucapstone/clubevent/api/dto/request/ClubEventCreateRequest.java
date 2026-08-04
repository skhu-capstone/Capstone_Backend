package com.skhu.skhucapstone.clubevent.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ClubEventCreateRequest(

        @NotBlank(message = "일정 제목을 입력해주세요.")
        @Size(max = 100, message = "일정 제목은 100자 이하로 입력해주세요.")
        String title,

        @Size(max = 3000, message = "일정 설명은 3000자 이하로 입력해주세요.")
        String description,

        @NotNull(message = "일정 시작 시간을 입력해주세요.")
        LocalDateTime startAt,

        @NotNull(message = "일정 종료 시간을 입력해주세요.")
        LocalDateTime endAt,

        @Size(max = 255, message = "일정 장소는 255자 이하로 입력해주세요.")
        String location

) {
}