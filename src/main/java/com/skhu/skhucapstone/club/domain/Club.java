package com.skhu.skhucapstone.club.domain;

import jakarta.persistence.*;
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

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "short_description", nullable = false, length = 255)
    private String shortDescription;

    @Column(name = "detail_description", nullable = false, columnDefinition = "TEXT")
    private String detailDescription;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "regular_meeting_time", nullable = false, length = 100)
    private String regularMeetingTime;

    @Column(name = "activity_location", nullable = false, length = 255)
    private String activityLocation;

    @Column(name = "contact", nullable = false, length = 100)
    private String contact;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Club(
            String clubName,
            String category,
            String shortDescription,
            String detailDescription,
            String imageUrl,
            String regularMeetingTime,
            String activityLocation,
            String contact
    ) {
        this.clubName = clubName;
        this.category = category;
        this.shortDescription = shortDescription;
        this.detailDescription = detailDescription;
        this.imageUrl = imageUrl;
        this.regularMeetingTime = regularMeetingTime;
        this.activityLocation = activityLocation;
        this.contact = contact;
        this.approved = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        this.approved = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {
        this.approved = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateInfo(
            String clubName,
            String category,
            String shortDescription,
            String detailDescription,
            String imageUrl,
            String regularMeetingTime,
            String activityLocation,
            String contact
    ) {
        this.clubName = clubName;
        this.category = category;
        this.shortDescription = shortDescription;
        this.detailDescription = detailDescription;
        this.imageUrl = imageUrl;
        this.regularMeetingTime = regularMeetingTime;
        this.activityLocation = activityLocation;
        this.contact = contact;
        this.updatedAt = LocalDateTime.now();
    }
}