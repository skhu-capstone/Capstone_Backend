package com.skhu.skhucapstone.clubcollaboration.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubCollabApplyResponse {

    private Long collabId;

    private String title;

    private Long chatRoomId;

    private Boolean isNew;
}