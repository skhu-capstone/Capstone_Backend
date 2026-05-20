package com.skhu.skhucapstone.club.api.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubCreateRequest(
        @NotBlank
        String clubName,

        @NotBlank
        String category,

        @NotBlank @Size(max = 255)
        String shortDescription,

        @NotBlank
        String detailDescription,

        String imageUrl,

        @NotBlank
        String regularMeetingTime,

        @NotBlank
        String activityLocation,

        @NotBlank String contact
) {
}