package com.skhu.skhucapstone.chat.dto.req;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatMessageReadReq {

    // 읽음 처리할 메시지 ID 목록
    private List<Long> messageIds;
}
