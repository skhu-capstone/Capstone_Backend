package com.skhu.skhucapstone.clubmember.api.dto.request;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import jakarta.validation.constraints.NotNull;

public record ClubMemberRoleUpdateRequest(

        @NotNull(message = "변경할 역할을 입력해주세요.")
        ClubRole role

) {
}