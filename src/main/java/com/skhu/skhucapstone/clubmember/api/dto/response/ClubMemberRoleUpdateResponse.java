package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubMemberRoleUpdateResponse {

    private Long clubId;

    private Long userId;

    private ClubRole role;
}