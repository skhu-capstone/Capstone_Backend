package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubJoinCancelResponse {

    private Long clubId;

    private Long userId;

    private ClubJoinStatus clubJoinStatus;
}