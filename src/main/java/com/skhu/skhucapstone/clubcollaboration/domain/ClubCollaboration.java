package com.skhu.skhucapstone.clubcollaboration.domain;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ClubCollaboration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collabId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String contestName;

    @Column(nullable = false)
    private LocalDate contestDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCollab(
            String title,
            String contestName,
            LocalDate contestDate,
            String content,
            LocalDate deadline,
            String imageUrl
    )
    {
        this.title = title;
        this.contestName = contestName;
        this.contestDate = contestDate;
        this.content = content;
        this.deadline = deadline;
        this.imageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now();
    }
}