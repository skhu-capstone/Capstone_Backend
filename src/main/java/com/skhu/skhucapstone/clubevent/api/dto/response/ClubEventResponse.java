package com.skhu.skhucapstone.clubevent.api.dto.response;

import com.skhu.skhucapstone.clubevent.domain.ClubEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ClubEventResponse {

    private Long eventId;
    private Long clubId;
    private Long creatorId;
    private String creatorName;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ClubEventResponse from(ClubEvent clubEvent) {
        return ClubEventResponse.builder()
                .eventId(clubEvent.getId())
                .clubId(clubEvent.getClub().getId())
                .creatorId(clubEvent.getCreator().getUserId())
                .creatorName(clubEvent.getCreator().getName())
                .title(clubEvent.getTitle())
                .description(clubEvent.getDescription())
                .startAt(clubEvent.getStartAt())
                .endAt(clubEvent.getEndAt())
                .location(clubEvent.getLocation())
                .createdAt(clubEvent.getCreatedAt())
                .updatedAt(clubEvent.getUpdatedAt())
                .build();
    }
}