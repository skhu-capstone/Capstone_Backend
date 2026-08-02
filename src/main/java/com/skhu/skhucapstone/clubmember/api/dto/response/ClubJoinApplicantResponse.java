package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ClubJoinApplicantResponse {

    private Long userId;

    private String name;

    private String profileImage;

    private String joinMessage;

    private ClubJoinStatus clubJoinStatus;

    private LocalDateTime requestedAt;
}