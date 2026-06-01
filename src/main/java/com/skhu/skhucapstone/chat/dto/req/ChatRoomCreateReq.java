package com.skhu.skhucapstone.chat.dto.req;

import lombok.Getter;

@Getter
public class ChatRoomCreateReq {

    // 채팅 상대방 userId
    private Long targetUserId;
}
