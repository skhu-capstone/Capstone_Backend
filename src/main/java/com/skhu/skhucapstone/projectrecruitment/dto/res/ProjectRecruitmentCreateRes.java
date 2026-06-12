package com.skhu.skhucapstone.projectrecruitment.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectRecruitmentCreateRes {

    private Long projectRecruitmentId;

    private Long userId;

    private String writerName;

    private String writerStack;

    private String title;

    private String imageUrl;

    private String positions;

    private String content;

    private LocalDate deadline;

    private LocalDateTime createdAt;
}
