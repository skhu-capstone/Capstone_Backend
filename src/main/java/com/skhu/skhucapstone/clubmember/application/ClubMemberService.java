package com.skhu.skhucapstone.clubmember.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberListResponse;
import com.skhu.skhucapstone.clubmember.api.dto.response.MyClubResponse;
import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    public List<ClubMemberListResponse> getClubMembers(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        return clubMemberRepository
                .findByClubAndClubJoinStatus(
                        club,
                        ClubJoinStatus.JOINED
                )
                .stream()
                .map(clubMember -> ClubMemberListResponse.builder()
                        .userId(clubMember.getUser().getUserId())
                        .name(clubMember.getUser().getName())
                        .profileImage(clubMember.getUser().getProfileImage())
                        .role(clubMember.getRole())
                        .build())
                .toList();
    }

    public List<MyClubResponse> getMyClubs(Long userId) {
        return clubMemberRepository
                .findByUserUserIdAndClubJoinStatus(
                        userId,
                        ClubJoinStatus.JOINED
                )
                .stream()
                .map(clubMember -> MyClubResponse.builder()
                        .clubId(clubMember.getClub().getId())
                        .clubName(clubMember.getClub().getClubName())
                        .imageUrl(clubMember.getClub().getImageUrl())
                        .category(clubMember.getClub().getCategory())
                        .build())
                .toList();
    }
}