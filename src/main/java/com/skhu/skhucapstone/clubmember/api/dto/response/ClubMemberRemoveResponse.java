package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubMemberRemoveResponse {

    private Long clubId;

    private Long userId;

    private ClubJoinStatus clubJoinStatus;
}