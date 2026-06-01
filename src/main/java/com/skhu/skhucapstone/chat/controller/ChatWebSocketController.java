package com.skhu.skhucapstone.chat.controller;

import com.skhu.skhucapstone.chat.dto.req.ChatMessageSendReq;
import com.skhu.skhucapstone.chat.dto.res.ChatMessageRes;
import com.skhu.skhucapstone.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 클라이언트가 /app/chat/{chatRoomId}/send 로 메시지를 보내면
    // 채팅방 구독자 전체에게 /topic/chat/{chatRoomId} 로 브로드캐스트
    @MessageMapping("/chat/{chatRoomId}/send")
    public void sendMessage(
            @DestinationVariable Long chatRoomId,
            @Payload ChatMessageSendReq req,
            Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        ChatMessageRes res = chatService.sendMessage(userId, chatRoomId, req);
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, res);
    }
}
