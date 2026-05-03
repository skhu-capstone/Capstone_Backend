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
        Boolean isApproved,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClubResponse from(Club club) {
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
                club.isApproved(),
                club.getCreatedAt(),
                club.getUpdatedAt()
        );
    }
}