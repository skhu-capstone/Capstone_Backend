package com.skhu.skhucapstone.chat.repository;

import com.skhu.skhucapstone.chat.entity.ChatMessage;
import com.skhu.skhucapstone.chat.entity.ChatRoom;
import com.skhu.skhucapstone.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 채팅방 메시지 목록 조회 (페이징, 오래된 순 정렬)
    // 단순 조건 + 정렬이라 메서드명으로 표현 가능
    Page<ChatMessage> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom, Pageable pageable);

    // 채팅방의 안 읽은 메시지 수 조회 (채팅방 목록에서 unreadCount 표시용)
    // 상대방이 보낸 메시지 중 아직 읽지 않은 것만 카운트 → 복합 조건이라 @Query 사용
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.chatRoom = :chatRoom AND m.sender <> :user AND m.isRead = false")
    long countUnreadMessages(@Param("chatRoom") ChatRoom chatRoom, @Param("user") User user);

    // 채팅방의 가장 마지막 메시지 조회 (채팅방 목록에서 lastMessage 표시용)
    // JPQL은 LIMIT을 지원하지 않아 메서드 네이밍으로 처리
    Optional<ChatMessage> findFirstByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    // 읽음 처리 대상 메시지 조회 (messageIds 목록으로 한 번에 조회)
    List<ChatMessage> findAllByChatMessageIdIn(List<Long> ids);
}
