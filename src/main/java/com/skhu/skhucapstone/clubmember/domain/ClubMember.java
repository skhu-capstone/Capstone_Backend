package com.skhu.skhucapstone.clubmember.domain;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "club_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ClubRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_join_status", nullable = false)
    private ClubJoinStatus clubJoinStatus;

    @Column(name = "join_message", length = 500)
    private String joinMessage;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public ClubMember(
            Club club,
            User user,
            ClubRole role,
            ClubJoinStatus clubJoinStatus,
            String joinMessage
    ) {
        LocalDateTime now = LocalDateTime.now();

        this.club = club;
        this.user = user;
        this.role = role;
        this.clubJoinStatus = clubJoinStatus;
        this.joinMessage = joinMessage;
        this.requestedAt = clubJoinStatus == ClubJoinStatus.PENDING
                ? now
                : null;
        this.joinedAt = clubJoinStatus == ClubJoinStatus.JOINED
                ? now
                : null;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public void approveJoin() {
        this.role = ClubRole.MEMBER;
        this.clubJoinStatus = ClubJoinStatus.JOINED;
        this.joinedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void rejectJoin() {
        this.clubJoinStatus = ClubJoinStatus.REJECTED;
        this.joinedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    public void reapply(String joinMessage) {
        LocalDateTime now = LocalDateTime.now();

        this.role = ClubRole.MEMBER;
        this.clubJoinStatus = ClubJoinStatus.PENDING;
        this.joinMessage = joinMessage;
        this.requestedAt = now;
        this.joinedAt = null;
        this.updatedAt = now;
    }

    public void changeRole(ClubRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }

    public void withdraw() {
        this.clubJoinStatus = ClubJoinStatus.WITHDRAWN;
        this.joinedAt = null;
        this.updatedAt = LocalDateTime.now();
    }
}