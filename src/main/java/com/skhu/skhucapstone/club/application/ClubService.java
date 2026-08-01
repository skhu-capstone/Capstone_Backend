package com.skhu.skhucapstone.club.application;

import com.skhu.skhucapstone.club.api.dto.Request.ClubCreateRequest;
import com.skhu.skhucapstone.club.api.dto.Response.ClubListResponse;
import com.skhu.skhucapstone.club.api.dto.Response.ClubPageResponse;
import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.common.file.ImageUploadService;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;
    private final ImageUploadService imageUploadService;

    @Transactional
    public ClubResponse createClub(Long userId, ClubCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean alreadyPresident =
                clubMemberRepository.existsByUserUserIdAndRoleAndClubJoinStatus(
                        userId,
                        ClubRole.PRESIDENT,
                        ClubJoinStatus.JOINED
                );

        if (alreadyPresident) {
            throw new CustomException(ErrorCode.CLUB_PRESIDENT_ALREADY_EXISTS);
        }

        Club club = Club.builder()
                .clubName(request.clubName())
                .category(request.category())
                .shortDescription(request.shortDescription())
                .detailDescription(request.detailDescription())
                .imageUrl(request.imageUrl())
                .regularMeetingTime(request.regularMeetingTime())
                .activityLocation(request.activityLocation())
                .contact(request.contact())
                .build();

        Club savedClub = clubRepository.save(club);

        ClubMember president = ClubMember.builder()
                .club(savedClub)
                .user(user)
                .role(ClubRole.PRESIDENT)
                .clubJoinStatus(ClubJoinStatus.JOINED)
                .build();

        clubMemberRepository.save(president);

        return ClubResponse.from(savedClub, 1L);
    }

    public ClubPageResponse getClubs(
            String keyword,
            String category,
            int page,
            int size
    ) {
        validateSearchCondition(page, size);

        Pageable pageable = PageRequest.of(page, size);

        String searchKeyword = (keyword == null || keyword.isBlank())
                ? null
                : keyword;

        String searchCategory = (category == null || category.isBlank())
                ? null
                : category;

        Page<Club> clubs = clubRepository.searchClubs(
                searchKeyword,
                searchCategory,
                pageable
        );

        return ClubPageResponse.builder()
                .content(clubs.getContent()
                        .stream()
                        .map(ClubListResponse::from)
                        .toList())
                .page(clubs.getNumber())
                .size(clubs.getSize())
                .totalElements(clubs.getTotalElements())
                .totalPages(clubs.getTotalPages())
                .build();
    }

    public ClubResponse getClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        long memberCount =
                clubMemberRepository.countByClubAndClubJoinStatus(
                        club,
                        ClubJoinStatus.JOINED
                );

        return ClubResponse.from(club, memberCount);
    }

    @Transactional
    public String uploadClubImage(Long clubId, MultipartFile file) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 ID의 동아리를 찾을 수 없습니다. clubId = " + clubId
                ));

        if (club.getImageUrl() != null) {
            imageUploadService.delete(club.getImageUrl());
        }

        String imageUrl = imageUploadService.upload(file, "club");
        club.updateImage(imageUrl);

        return imageUrl;
    }

    private void validateSearchCondition(int page, int size) {
        if (page < 0 || size < 1) {
            throw new CustomException(ErrorCode.INVALID_SEARCH_CONDITION);
        }
    }
}