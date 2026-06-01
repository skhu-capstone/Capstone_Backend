package com.skhu.skhucapstone.chat.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomListItemRes {

    private Long chatRoomId;
    private Long targetUserId;
    private String targetUserName;
    private String targetProfileImage;
    // 마지막 메시지 내용 (없으면 null)
    private String lastMessage;
    // 마지막 메시지 전송 시각 (없으면 null)
    private LocalDateTime lastMessageAt;
    private long unreadCount;
}
