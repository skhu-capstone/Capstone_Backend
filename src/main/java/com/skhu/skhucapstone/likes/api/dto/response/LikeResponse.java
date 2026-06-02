package com.skhu.skhucapstone.likes.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponse {

    private boolean liked;

    private long likeCount;
}