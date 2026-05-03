package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;

import java.time.LocalDateTime;

public record ClubMemberRegisterResponse(
        Long clubId,
        int registeredCount,
        ClubJoinStatus clubJoinStatus,
        LocalDateTime updatedAt
) {
}