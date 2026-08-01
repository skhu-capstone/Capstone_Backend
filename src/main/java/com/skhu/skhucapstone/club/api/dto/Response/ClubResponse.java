package com.skhu.skhucapstone.club.api.dto.Response;

import com.skhu.skhucapstone.club.domain.Club;

import java.time.LocalDateTime;

public record ClubResponse(
        Long id,
        String clubName,
        String category,
        String shortDescription,
        String detailDescription,
        String imageUrl,
        String regularMeetingTime,
        String activityLocation,
        String contact,
        long memberCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClubResponse from(Club club, long memberCount) {
        return new ClubResponse(
                club.getId(),
                club.getClubName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getDetailDescription(),
                club.getImageUrl(),
                club.getRegularMeetingTime(),
                club.getActivityLocation(),
                club.getContact(),
                memberCount,
                club.getCreatedAt(),
                club.getUpdatedAt()
        );
    }
}