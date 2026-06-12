package com.skhu.skhucapstone.projectrecruitment.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectRecruitmentPageRes {

    private List<ProjectRecruitmentListRes> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
}
