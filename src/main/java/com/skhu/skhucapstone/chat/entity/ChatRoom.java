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
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    // 채팅방을 생성 요청한 유저 (커피챗 신청자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    // 채팅 상대방 (커피챗 대상자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    // 채팅방 생성 시각 (한번만 저장, 이후 변경 불가)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 마지막 메시지 전송 시각 (채팅방 목록 정렬에 사용)
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 엔티티 최초 저장 시 createdAt, updatedAt 자동 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 메시지 전송 시 채팅방의 updatedAt 갱신 (채팅방 목록 최신순 정렬용)
    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
