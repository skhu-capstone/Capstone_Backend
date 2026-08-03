package com.skhu.skhucapstone.club.api.dto.Response;

import com.skhu.skhucapstone.club.domain.Club;

public record ClubListResponse(
        Long id,
        String clubName,
        String category,
        String shortDescription,
        String imageUrl
) {
    public static ClubListResponse from(Club club) {
        return new ClubListResponse(
                club.getId(),
                club.getClubName(),
                club.getCategory(),
                club.getShortDescription(),
                club.getImageUrl()
        );
    }
}