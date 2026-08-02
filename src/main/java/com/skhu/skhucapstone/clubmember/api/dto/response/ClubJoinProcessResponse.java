package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ClubJoinProcessResponse {

    private Long clubId;

    private Long userId;

    private ClubRole role;

    private ClubJoinStatus clubJoinStatus;

    private LocalDateTime joinedAt;
}