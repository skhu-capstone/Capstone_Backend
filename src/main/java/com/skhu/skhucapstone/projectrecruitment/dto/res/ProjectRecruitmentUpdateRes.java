package com.skhu.skhucapstone.projectrecruitment.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectRecruitmentUpdateRes {

    private Long projectRecruitmentId;

    private String title;

    private String imageUrl;

    private String writerStack;

    private String positions;

    private String content;

    private LocalDate deadline;

    private LocalDateTime updatedAt;
}
