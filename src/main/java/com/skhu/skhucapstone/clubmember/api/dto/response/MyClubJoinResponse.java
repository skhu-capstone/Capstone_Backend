package com.skhu.skhucapstone.clubmember.api.dto.response;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyClubJoinResponse {

    private Long clubId;

    private String clubName;

    private String category;

    private String imageUrl;

    private String joinMessage;

    private ClubJoinStatus clubJoinStatus;

    private LocalDateTime requestedAt;
}