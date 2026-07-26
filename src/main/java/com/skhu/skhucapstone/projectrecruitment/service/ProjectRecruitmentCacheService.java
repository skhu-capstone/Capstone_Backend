package com.skhu.skhucapstone.projectrecruitment.service;

import com.skhu.skhucapstone.common.config.CacheConfig;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentDetailRes;
import com.skhu.skhucapstone.projectrecruitment.entity.ProjectRecruitment;
import com.skhu.skhucapstone.projectrecruitment.repository.ProjectRecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Cacheable은 프록시를 통해서만 동작하므로 같은 클래스 안에서 호출하면 캐시가 적용되지 않는다.
// 그래서 캐시 대상 조회를 별도 빈으로 분리했다.
@Service
@RequiredArgsConstructor
public class ProjectRecruitmentCacheService {

    private final ProjectRecruitmentRepository projectRecruitmentRepository;

    // 존재하지 않는 ID는 예외가 던져져 캐시에 저장되지 않는다.
    // dDay는 조회 시점 기준 값이라 캐시에 넣지 않고 호출부에서 계산한다.
    @Cacheable(cacheNames = CacheConfig.PROJECT_RECRUITMENT_CACHE, key = "#projectRecruitmentId")
    @Transactional(readOnly = true)
    public ProjectRecruitmentDetailRes getRecruitmentDetail(Long projectRecruitmentId) {
        ProjectRecruitment recruitment = projectRecruitmentRepository.findById(projectRecruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_RECRUITMENT_NOT_FOUND));

        return ProjectRecruitmentDetailRes.builder()
                .projectRecruitmentId(recruitment.getProjectRecruitmentId())
                .writerId(recruitment.getUser().getUserId())
                .title(recruitment.getTitle())
                .imageUrl(recruitment.getImageUrl())
                .writerName(recruitment.getUser().getName())
                .writerStack(recruitment.getWriterStack())
                .positions(recruitment.getPositions())
                .content(recruitment.getContent())
                .deadline(recruitment.getDeadline())
                .createdAt(recruitment.getCreatedAt())
                .build();
    }
}
