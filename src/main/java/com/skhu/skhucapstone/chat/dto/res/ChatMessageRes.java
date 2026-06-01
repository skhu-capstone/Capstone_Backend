package com.skhu.skhucapstone.chat.dto.res;

import com.skhu.skhucapstone.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageRes {

    private Long chatMessageId;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public static ChatMessageRes from(ChatMessage message) {
        return ChatMessageRes.builder()
                .chatMessageId(message.getChatMessageId())
                .chatRoomId(message.getChatRoom().getChatRoomId())
                .senderId(message.getSender().getUserId())
                .content(message.getContent())
                .isRead(message.getIsRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
