package com.skhu.skhucapstone.clubmember.api.dto.request;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;

import java.util.List;

public record ClubMemberRegisterRequest(
        List<MemberInfo> members
) {

    public record MemberInfo(
            Long userId,
            ClubRole role
    ) {
    }
}