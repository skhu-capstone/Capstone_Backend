package com.skhu.skhucapstone.chat.dto.res;

import com.skhu.skhucapstone.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomRes {

    private Long chatRoomId;
    private Long user1Id;
    private Long user2Id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 새로 생성된 채팅방이면 true, 기존 채팅방 반환이면 false
    private Boolean isNew;

    public static ChatRoomRes of(ChatRoom chatRoom, Boolean isNew) {
        return ChatRoomRes.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .user1Id(chatRoom.getUser1().getUserId())
                .user2Id(chatRoom.getUser2().getUserId())
                .createdAt(chatRoom.getCreatedAt())
                .updatedAt(chatRoom.getUpdatedAt())
                .isNew(isNew)
                .build();
    }
}
