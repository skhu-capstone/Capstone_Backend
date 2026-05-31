package com.skhu.skhucapstone.chat.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageReadRes {

    private Long chatRoomId;
    // 실제로 읽음 처리된 메시지 수
    private int readCount;
}
