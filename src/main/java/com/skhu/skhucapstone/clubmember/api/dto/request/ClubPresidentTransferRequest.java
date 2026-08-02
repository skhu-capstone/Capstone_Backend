package com.skhu.skhucapstone.clubmember.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record ClubPresidentTransferRequest(

        @NotNull(message = "새 대표의 사용자 ID를 입력해주세요.")
        Long newPresidentUserId

) {
}