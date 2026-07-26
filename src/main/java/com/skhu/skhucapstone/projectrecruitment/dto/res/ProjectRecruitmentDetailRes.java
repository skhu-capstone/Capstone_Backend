package com.skhu.skhucapstone.projectrecruitment.dto.res;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Redis 캐시에 JSON으로 저장되므로 Jackson이 기본 지원하는 record로 정의한다.
// dDay는 조회 시점마다 달라지는 값이라 캐시에는 null로 저장하고,
// 서비스에서 toBuilder()로 매번 새로 계산해 채운다.
@Builder(toBuilder = true)
public record ProjectRecruitmentDetailRes(
        Long projectRecruitmentId,
        Long writerId,
        String title,
        String imageUrl,
        String writerName,
        String writerStack,
        String positions,
        String content,
        LocalDate deadline,
        String dDay,
        LocalDateTime createdAt
) {
}
