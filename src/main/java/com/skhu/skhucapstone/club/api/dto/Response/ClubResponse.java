package com.skhu.skhucapstone.club.api.dto.Response;

import com.skhu.skhucapstone.club.domain.Club;

import java.time.LocalDateTime;

public record ClubResponse(
        Long id,
        String clubName,
        String description,
        Boolean isApproved,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClubResponse from(Club club) {
        return new ClubResponse(
                club.getId(),
                club.getClubName(),
                club.getDescription(),
                club.isApproved(),
                club.getCreatedAt(),
                club.getUpdatedAt()
        );
    }
}