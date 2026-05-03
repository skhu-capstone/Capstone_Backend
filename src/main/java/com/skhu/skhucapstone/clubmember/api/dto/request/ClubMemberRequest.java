package com.skhu.skhucapstone.clubmember.api.dto.request;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClubMemberRequest(
        @NotEmpty
        List<MemberInfo> members
) {

    public record MemberInfo(
            @NotNull
            Long userId,
            @NotNull
            ClubRole role
    ) {
    }
}