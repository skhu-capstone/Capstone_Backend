package com.skhu.skhucapstone.club.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "club")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_name", nullable = false, length = 100)
    private String clubName;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Club(String clubName, String description) {
        this.clubName = clubName;
        this.description = description;
        this.isApproved = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        this.isApproved = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {
        this.isApproved = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateInfo(String clubName, String description) {
        this.clubName = clubName;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}