package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubMemberListResponse {

    private Long userId;

    private String name;

    private String profileImage;

    private ClubRole role;
}