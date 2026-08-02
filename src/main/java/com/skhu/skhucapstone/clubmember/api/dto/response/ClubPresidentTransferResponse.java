package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubPresidentTransferResponse {

    private Long clubId;

    private Long previousPresidentUserId;

    private ClubRole previousPresidentRole;

    private Long newPresidentUserId;

    private ClubRole newPresidentRole;
}