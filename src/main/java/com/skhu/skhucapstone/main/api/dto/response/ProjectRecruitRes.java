package com.skhu.skhucapstone.main.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProjectRecruitRes {

    private Long projectRecruitmentId;

    private String title;

    private String content;

    private String imageUrl;

    private LocalDate deadline;

    private String dDay;
}