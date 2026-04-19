package com.skhu.skhucapstone.clubmember.domain;

import com.skhu.skhucapstone.club.domain.Club;
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

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;


    @Column(name = "user_id", nullable = false)
    private Long userId; //아직 유저 엔티티가 없음


    @Builder
    public ClubMember(Club club, Long userId, ClubRole role) {
        this.club = club;
        this.userId = userId;
        this.role = role;
        this.clubJoinStatus = ClubJoinStatus.JOINED;
        this.joinedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void changeRole(ClubRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }

    public void withdraw() {
        this.clubJoinStatus = ClubJoinStatus.NONE;
        this.updatedAt = LocalDateTime.now();
    }
}