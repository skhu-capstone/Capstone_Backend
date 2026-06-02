package com.skhu.skhucapstone.chat.repository;

import com.skhu.skhucapstone.chat.entity.ChatRoom;
import com.skhu.skhucapstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 두 유저 사이의 채팅방 조회 (채팅방 생성 시 중복 방지용)
    // user1-user2, user2-user1 양방향 모두 확인해야 해서 @Query 사용
    @Query("SELECT c FROM ChatRoom c WHERE (c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1)")
    Optional<ChatRoom> findByUsers(@Param("user1") User user1, @Param("user2") User user2);

    // 내 채팅방 목록 조회 (최신 메시지 순 정렬)
    // user1이거나 user2인 채팅방 모두 포함해야 해서 OR 조건 + ORDER BY 필요
    @Query("SELECT c FROM ChatRoom c WHERE c.user1 = :user OR c.user2 = :user ORDER BY c.updatedAt DESC")
    List<ChatRoom> findAllByUser(@Param("user") User user);
}
