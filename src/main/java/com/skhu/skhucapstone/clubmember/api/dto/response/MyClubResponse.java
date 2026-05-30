package com.skhu.skhucapstone.clubmember.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyClubResponse {

    private Long clubId;

    private String clubName;

    private String imageUrl;

    private String category;
}