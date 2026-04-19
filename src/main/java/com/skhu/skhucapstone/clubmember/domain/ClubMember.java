package com.skhu.skhucapstone.clubmember.domain;

import com.skhu.skhucapstone.club.domain.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClubRole role;

    private LocalDateTime joinedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    private Long userId; //아직 유저 엔티티가 없음


    public ClubMember(Club club, Long userId, ClubRole role) {
        this.club = club;
        this.userId = userId;
        this.role = role;
        this.joinedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void changeRole(ClubRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }
}