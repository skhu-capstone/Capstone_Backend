package com.skhu.skhucapstone.projectrecruitment.service;

import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.projectrecruitment.dto.req.ProjectRecruitmentCreateReq;
import com.skhu.skhucapstone.projectrecruitment.dto.req.ProjectRecruitmentUpdateReq;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentCreateRes;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentDeleteRes;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentDetailRes;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentListRes;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentPageRes;
import com.skhu.skhucapstone.projectrecruitment.dto.res.ProjectRecruitmentUpdateRes;
import com.skhu.skhucapstone.projectrecruitment.entity.ProjectRecruitment;
import com.skhu.skhucapstone.projectrecruitment.repository.ProjectRecruitmentRepository;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectRecruitmentService {

    private final ProjectRecruitmentRepository projectRecruitmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProjectRecruitmentCreateRes createRecruitment(
            Long userId,
            ProjectRecruitmentCreateReq request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateDeadline(request.deadline());

        ProjectRecruitment recruitment = ProjectRecruitment.builder()
                .title(request.title())
                .imageUrl(request.imageUrl())
                .writerStack(request.writerStack())
                .positions(request.positions())
                .content(request.content())
                .deadline(request.deadline())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        ProjectRecruitment savedRecruitment = projectRecruitmentRepository.save(recruitment);

        return ProjectRecruitmentCreateRes.builder()
                .projectRecruitmentId(savedRecruitment.getProjectRecruitmentId())
                .userId(user.getUserId())
                .writerName(user.getName())
                .writerStack(savedRecruitment.getWriterStack())
                .title(savedRecruitment.getTitle())
                .imageUrl(savedRecruitment.getImageUrl())
                .positions(savedRecruitment.getPositions())
                .content(savedRecruitment.getContent())
                .deadline(savedRecruitment.getDeadline())
                .createdAt(savedRecruitment.getCreatedAt())
                .build();
    }

    public ProjectRecruitmentPageRes getRecruitments(
            String keyword,
            int page,
            int size
    ) {
        validateSearchCondition(page, size);

        Pageable pageable = PageRequest.of(page, size);

        String searchKeyword = (keyword == null || keyword.isBlank())
                ? null
                : keyword;

        Page<ProjectRecruitment> recruitments =
                projectRecruitmentRepository.searchRecruitments(searchKeyword, pageable);

        return ProjectRecruitmentPageRes.builder()
                .content(recruitments.getContent()
                        .stream()
                        .map(this::toListResponse)
                        .toList())
                .page(recruitments.getNumber())
                .size(recruitments.getSize())
                .totalElements(recruitments.getTotalElements())
                .totalPages(recruitments.getTotalPages())
                .build();
    }

    public ProjectRecruitmentDetailRes getRecruitment(Long projectRecruitmentId) {
        ProjectRecruitment recruitment = findRecruitment(projectRecruitmentId);

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
                .dDay(calculateDday(recruitment.getDeadline()))
                .createdAt(recruitment.getCreatedAt())
                .build();
    }

    @Transactional
    public ProjectRecruitmentUpdateRes updateRecruitment(
            Long projectRecruitmentId,
            Long userId,
            ProjectRecruitmentUpdateReq request
    ) {
        ProjectRecruitment recruitment = findRecruitment(projectRecruitmentId);

        validateWriter(recruitment, userId, ErrorCode.PROJECT_RECRUITMENT_UPDATE_ACCESS_DENIED);

        validateDeadline(request.deadline());

        recruitment.updateRecruitment(
                request.title(),
                request.imageUrl(),
                request.writerStack(),
                request.positions(),
                request.content(),
                request.deadline()
        );

        return ProjectRecruitmentUpdateRes.builder()
                .projectRecruitmentId(recruitment.getProjectRecruitmentId())
                .title(recruitment.getTitle())
                .imageUrl(recruitment.getImageUrl())
                .writerStack(recruitment.getWriterStack())
                .positions(recruitment.getPositions())
                .content(recruitment.getContent())
                .deadline(recruitment.getDeadline())
                .updatedAt(recruitment.getUpdatedAt())
                .build();
    }

    @Transactional
    public ProjectRecruitmentDeleteRes deleteRecruitment(
            Long projectRecruitmentId,
            Long userId
    ) {
        ProjectRecruitment recruitment = findRecruitment(projectRecruitmentId);

        validateWriter(recruitment, userId, ErrorCode.PROJECT_RECRUITMENT_DELETE_ACCESS_DENIED);

        projectRecruitmentRepository.delete(recruitment);

        return ProjectRecruitmentDeleteRes.builder()
                .projectRecruitmentId(projectRecruitmentId)
                .build();
    }

    private ProjectRecruitment findRecruitment(Long projectRecruitmentId) {
        return projectRecruitmentRepository.findById(projectRecruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_RECRUITMENT_NOT_FOUND));
    }

    private void validateWriter(
            ProjectRecruitment recruitment,
            Long userId,
            ErrorCode errorCode
    ) {
        if (!recruitment.getUser().getUserId().equals(userId)) {
            throw new CustomException(errorCode);
        }
    }

    private void validateDeadline(LocalDate deadline) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.INVALID_PROJECT_RECRUITMENT_REQUEST);
        }
    }

    private void validateSearchCondition(int page, int size) {
        if (page < 0 || size < 1) {
            throw new CustomException(ErrorCode.INVALID_SEARCH_CONDITION);
        }
    }

    private ProjectRecruitmentListRes toListResponse(ProjectRecruitment recruitment) {
        return ProjectRecruitmentListRes.builder()
                .projectRecruitmentId(recruitment.getProjectRecruitmentId())
                .title(recruitment.getTitle())
                .content(recruitment.getContent())
                .imageUrl(recruitment.getImageUrl())
                .deadline(recruitment.getDeadline())
                .dDay(calculateDday(recruitment.getDeadline()))
                .build();
    }

    private String calculateDday(LocalDate deadline) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), deadline);

        if (days > 0) {
            return "D-" + days;
        }

        if (days == 0) {
            return "D-DAY";
        }

        return "마감";
    }
}
