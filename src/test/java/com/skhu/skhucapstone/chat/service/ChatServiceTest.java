package com.skhu.skhucapstone.chat.service;

import com.skhu.skhucapstone.chat.dto.req.ChatMessageSendReq;
import com.skhu.skhucapstone.chat.dto.req.ChatRoomCreateReq;
import com.skhu.skhucapstone.chat.dto.res.ChatMessageRes;
import com.skhu.skhucapstone.chat.entity.ChatMessage;
import com.skhu.skhucapstone.chat.entity.ChatRoom;
import com.skhu.skhucapstone.chat.repository.ChatMessageRepository;
import com.skhu.skhucapstone.chat.repository.ChatRoomRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChatService chatService;

    private final User user1 = User.builder().userId(1L).name("참여자1").build();
    private final User user2 = User.builder().userId(2L).name("참여자2").build();
    private final User outsider = User.builder().userId(3L).name("외부인").build();

    private ChatRoom chatRoom() {
        return ChatRoom.builder()
                .chatRoomId(10L)
                .user1(user1)
                .user2(user2)
                .build();
    }

    private ChatMessageSendReq sendReq(String content) {
        ChatMessageSendReq req = new ChatMessageSendReq();
        ReflectionTestUtils.setField(req, "content", content);
        return req;
    }

    @Test
    @DisplayName("채팅방 참여자는 메시지를 전송할 수 있다")
    void participantCanSendMessage() {
        ChatRoom room = chatRoom();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(chatRoomRepository.findById(10L)).thenReturn(Optional.of(room));
        when(chatMessageRepository.save(any(ChatMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatMessageRes res = chatService.sendMessage(1L, 10L, sendReq("안녕하세요"));

        assertThat(res.getContent()).isEqualTo("안녕하세요");
        assertThat(res.getSenderId()).isEqualTo(1L);
        assertThat(res.getChatRoomId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("채팅방에 참여하지 않은 사용자의 전송은 거부된다")
    void nonParticipantCannotSendMessage() {
        when(userRepository.findById(3L)).thenReturn(Optional.of(outsider));
        when(chatRoomRepository.findById(10L)).thenReturn(Optional.of(chatRoom()));

        assertThatThrownBy(() -> chatService.sendMessage(3L, 10L, sendReq("몰래 전송")))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.CHAT_ROOM_ACCESS_DENIED);

        verify(chatMessageRepository, never()).save(any());
    }

    @Test
    @DisplayName("전송된 메시지는 보낸 사람, 채팅방, 내용과 함께 저장된다")
    void sentMessageIsSaved() {
        ChatRoom room = chatRoom();
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(chatRoomRepository.findById(10L)).thenReturn(Optional.of(room));
        when(chatMessageRepository.save(any(ChatMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        chatService.sendMessage(2L, 10L, sendReq("저장 확인용 메시지"));

        ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(ChatMessage.class);
        verify(chatMessageRepository).save(captor.capture());
        ChatMessage saved = captor.getValue();
        assertThat(saved.getContent()).isEqualTo("저장 확인용 메시지");
        assertThat(saved.getSender().getUserId()).isEqualTo(2L);
        assertThat(saved.getChatRoom().getChatRoomId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("존재하지 않는 채팅방에는 메시지를 보낼 수 없다")
    void cannotSendMessageToNonexistentRoom() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(chatRoomRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatService.sendMessage(1L, 999L, sendReq("아무도 없는 방")))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.CHAT_ROOM_NOT_FOUND);

        verify(chatMessageRepository, never()).save(any());
    }

    @Test
    @DisplayName("자기 자신과는 채팅방을 만들 수 없다")
    void cannotCreateChatRoomWithSelf() {
        ChatRoomCreateReq req = new ChatRoomCreateReq();
        ReflectionTestUtils.setField(req, "targetUserId", 1L);

        assertThatThrownBy(() -> chatService.createOrGetChatRoom(1L, req))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.CANNOT_CHAT_WITH_SELF);
    }
}
