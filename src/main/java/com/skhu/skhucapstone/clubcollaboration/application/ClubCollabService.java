package com.skhu.skhucapstone.clubcollaboration.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubcollaboration.api.dto.request.ClubCollabCreateRequest;
import com.skhu.skhucapstone.clubcollaboration.api.dto.request.ClubCollabUpdateRequest;
import com.skhu.skhucapstone.clubcollaboration.api.dto.response.ClubCollabPageResponse;
import com.skhu.skhucapstone.clubcollaboration.api.dto.response.ClubCollabResponse;
import com.skhu.skhucapstone.clubcollaboration.domain.ClubCollaboration;
import com.skhu.skhucapstone.clubcollaboration.domain.repository.ClubCollabRepository;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
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
public class ClubCollabService {

    private final ClubCollabRepository clubCollabRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    public ClubCollabResponse createCollab(
            Long userId,
            ClubCollabCreateRequest request
    ) {
        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateCollabWritePermission(club, user);

        validateDate(request.contestDate(), request.deadline());

        ClubCollaboration clubCollaboration = ClubCollaboration.builder()
                .title(request.title())
                .contestName(request.contestName())
                .contestDate(request.contestDate())
                .content(request.content())
                .deadline(request.deadline())
                .imageUrl(request.imageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .club(club)
                .user(user)
                .build();

        ClubCollaboration savedCollab = clubCollabRepository.save(clubCollaboration);

        return toCollabResponse(savedCollab);
    }

    public ClubCollabPageResponse getCollabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ClubCollaboration> collabs =
                clubCollabRepository.findAllByOrderByCreatedAtDesc(pageable);

        return ClubCollabPageResponse.builder()
                .content(collabs.getContent()
                        .stream()
                        .map(this::toCollabResponse)
                        .toList())
                .page(collabs.getNumber())
                .size(collabs.getSize())
                .totalElements(collabs.getTotalElements())
                .totalPages(collabs.getTotalPages())
                .last(collabs.isLast())
                .build();
    }

    public ClubCollabResponse getCollab(Long collabId) {
        ClubCollaboration collab = clubCollabRepository.findById(collabId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_COLLAB_NOT_FOUND));

        return toCollabResponse(collab);
    }

    @Transactional
    public ClubCollabResponse updateCollab(
            Long collabId,
            Long userId,
            ClubCollabUpdateRequest request
    ) {
        ClubCollaboration collab = clubCollabRepository.findById(collabId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_COLLAB_NOT_FOUND));

        validateCollabWriter(collab, userId, ErrorCode.CLUB_COLLAB_UPDATE_FORBIDDEN);

        validateDate(request.contestDate(), request.deadline());

        collab.updateCollab(
                request.title(),
                request.contestName(),
                request.contestDate(),
                request.content(),
                request.deadline(),
                request.imageUrl()
        );

        return toCollabResponse(collab);
    }

    @Transactional
    public void deleteCollab(Long collabId, Long userId) {
        ClubCollaboration collab = clubCollabRepository.findById(collabId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_COLLAB_NOT_FOUND));

        validateCollabWriter(collab, userId, ErrorCode.CLUB_COLLAB_DELETE_FORBIDDEN);

        clubCollabRepository.delete(collab);
    }

    private void validateCollabWritePermission(Club club, User user) {
        ClubMember clubMember = clubMemberRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_COLLAB_WRITE_FORBIDDEN));

        if (clubMember.getRole() != ClubRole.STAFF &&
                clubMember.getRole() != ClubRole.PRESIDENT) {
            throw new CustomException(ErrorCode.CLUB_COLLAB_WRITE_FORBIDDEN);
        }
    }

    private void validateDate(LocalDate contestDate, LocalDate deadline){
        if (deadline.isAfter(contestDate)){
            throw new CustomException(ErrorCode.CLUB_COLLAB_INVALID_DATE);
        }
    }


    private void validateCollabWriter(
            ClubCollaboration collab,
            Long userId,
            ErrorCode errorCode
    ) {
        if (!collab.getUser().getUserId().equals(userId)) {
            throw new CustomException(errorCode);
        }
    }

    private ClubCollabResponse toCollabResponse(ClubCollaboration collab) {
        return ClubCollabResponse.builder()
                .collabId(collab.getCollabId())
                .clubId(collab.getClub().getId())
                .clubName(collab.getClub().getClubName())
                .title(collab.getTitle())
                .imageUrl(collab.getImageUrl())
                .contestName(collab.getContestName())
                .contestDate(collab.getContestDate())
                .content(collab.getContent())
                .deadline(collab.getDeadline())
                .dDayText(calculateDday(collab.getDeadline()))
                .writerName(collab.getUser().getName())
                .createdAt(collab.getCreatedAt())
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