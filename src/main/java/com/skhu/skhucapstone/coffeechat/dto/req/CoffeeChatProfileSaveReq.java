package com.skhu.skhucapstone.coffeechat.dto.req;

import com.skhu.skhucapstone.coffeechat.entity.MeetingType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoffeeChatProfileSaveReq {

    private String studentId;

    private String headline;

    private String interestTopics;

    private MeetingType meetingType;

    private String contactLink;

    private String introduction;
}
