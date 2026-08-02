package com.skhu.skhucapstone.club.api.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClubPageResponse {

    private List<ClubListResponse> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
}