package com.skhu.skhucapstone.clubevent.api.dto.response;

import com.skhu.skhucapstone.clubevent.domain.ClubEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ClubEventListResponse {

    private Long eventId;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String location;

    public static ClubEventListResponse from(ClubEvent clubEvent) {
        return ClubEventListResponse.builder()
                .eventId(clubEvent.getId())
                .title(clubEvent.getTitle())
                .startAt(clubEvent.getStartAt())
                .endAt(clubEvent.getEndAt())
                .location(clubEvent.getLocation())
                .build();
    }
}