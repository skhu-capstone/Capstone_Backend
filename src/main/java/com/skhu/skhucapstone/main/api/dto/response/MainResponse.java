package com.skhu.skhucapstone.main.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MainResponse {

    private List<CoffeeChatRes> recommendedCoffeeChats;

    private List<ProjectRecruitRes> projectRecruitments;

    private List<ClubCollabRes> clubCollaborations;

    private List<ClubFeedRes> clubFeeds;
}