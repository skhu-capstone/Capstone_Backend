package com.skhu.skhucapstone.clubmember.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubJoinApplicantResponse;
import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubManagementService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;

    public List<ClubJoinApplicantResponse> getJoinApplicants(
            Long clubId,
            Long userId
    ) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateJoinListPermission(club, user);

        return clubMemberRepository
                .findByClubAndClubJoinStatusOrderByRequestedAtDesc(
                        club,
                        ClubJoinStatus.PENDING
                )
                .stream()
                .map(clubMember -> ClubJoinApplicantResponse.builder()
                        .userId(clubMember.getUser().getUserId())
                        .name(clubMember.getUser().getName())
                        .profileImage(clubMember.getUser().getProfileImage())
                        .joinMessage(clubMember.getJoinMessage())
                        .clubJoinStatus(clubMember.getClubJoinStatus())
                        .requestedAt(clubMember.getRequestedAt())
                        .build())
                .toList();
    }

    private void validateJoinListPermission(
            Club club,
            User user
    ) {
        ClubMember clubMember = clubMemberRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.CLUB_JOIN_LIST_FORBIDDEN
                ));

        boolean joined =
                clubMember.getClubJoinStatus() == ClubJoinStatus.JOINED;

        boolean manager =
                clubMember.getRole() == ClubRole.PRESIDENT
                        || clubMember.getRole() == ClubRole.STAFF;

        if (!joined || !manager) {
            throw new CustomException(ErrorCode.CLUB_JOIN_LIST_FORBIDDEN);
        }
    }
}