package com.skhu.skhucapstone.chat.entity;

import com.skhu.skhucapstone.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    // 메시지가 속한 채팅방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    // 메시지 보낸 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // 메시지 내용
    @Column(nullable = false)
    private String content;

    // 읽음 여부 (기본값 false = 안 읽음)
    @Builder.Default
    @Column(nullable = false)
    private Boolean isRead = false;

    // 메시지 전송 시각 (한 번만 저장, 이후 변경 불가)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 엔티티 최초 저장 시 createdAt 자동 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // 읽음 처리 (상대방이 메시지를 읽었을 때 호출)
    public void markAsRead() {
        this.isRead = true;
    }
}
