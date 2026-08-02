package com.skhu.skhucapstone.club.api.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubUpdateRequest(

        @NotBlank(message = "동아리명을 입력해주세요.")
        @Size(max = 100, message = "동아리명은 100자 이하로 입력해주세요.")
        String clubName,

        @NotBlank(message = "카테고리를 입력해주세요.")
        @Size(max = 50, message = "카테고리는 50자 이하로 입력해주세요.")
        String category,

        @NotBlank(message = "한 줄 소개를 입력해주세요.")
        @Size(max = 255, message = "한 줄 소개는 255자 이하로 입력해주세요.")
        String shortDescription,

        @NotBlank(message = "상세 설명을 입력해주세요.")
        String detailDescription,

        String imageUrl,

        @NotBlank(message = "정기 모임 시간을 입력해주세요.")
        @Size(max = 100, message = "정기 모임 시간은 100자 이하로 입력해주세요.")
        String regularMeetingTime,

        @NotBlank(message = "활동 장소를 입력해주세요.")
        @Size(max = 255, message = "활동 장소는 255자 이하로 입력해주세요.")
        String activityLocation,

        @NotBlank(message = "연락처를 입력해주세요.")
        @Size(max = 100, message = "연락처는 100자 이하로 입력해주세요.")
        String contact

) {
}