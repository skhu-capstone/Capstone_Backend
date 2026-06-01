package com.skhu.skhucapstone.clubcollaboration.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClubCollabPageResponse {

    private List<ClubCollabResponse> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean last;
}