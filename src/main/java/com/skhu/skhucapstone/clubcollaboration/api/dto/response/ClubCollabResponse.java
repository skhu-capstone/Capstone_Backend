package com.skhu.skhucapstone.clubcollaboration.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ClubCollabResponse {

    private Long collabId;

    private Long clubId;

    private String clubName;

    private String title;

    private String contestName;

    private LocalDate contestDate;

    private String content;

    private LocalDate deadline;

    private String dDayText;

    private String writerName;

    private LocalDateTime createdAt;
}