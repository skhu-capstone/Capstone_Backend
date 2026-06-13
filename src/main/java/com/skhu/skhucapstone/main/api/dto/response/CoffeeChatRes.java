package com.skhu.skhucapstone.main.api.dto.response;

import com.skhu.skhucapstone.coffeechat.entity.MeetingType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoffeeChatRes {

    private Long coffeeChatProfileId;

    private Long userId;

    private String name;

    private String profileImageUrl;

    private String headline;

    private String interestTopics;

    private List<String> clubs;

    private MeetingType meetingType;
}