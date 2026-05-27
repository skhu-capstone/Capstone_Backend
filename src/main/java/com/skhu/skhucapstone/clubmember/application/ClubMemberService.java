package com.skhu.skhucapstone.clubmember.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.api.dto.request.ClubMemberRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberResponse;
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
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberListResponse;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    public ClubMemberResponse registerMembers(Long clubId, ClubMemberRequest request) {
        validateHasPresident(request);
        validateDuplicateUserInRequest(request);

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        List<ClubMember> clubMembers = request.members().stream()
                .map(memberInfo -> {
                    User user = userRepository.findById(memberInfo.userId())
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

                    validateAlreadyRegistered(club, user);

                    return ClubMember.builder()
                            .club(club)
                            .user(user)
                            .role(memberInfo.role())
                            .build();
                })
                .toList();

        clubMemberRepository.saveAll(clubMembers);

        return new ClubMemberResponse(
                club.getId(),
                clubMembers.size(),
                ClubJoinStatus.JOINED,
                LocalDateTime.now()
        );
    }

    private void validateHasPresident(ClubMemberRequest request) {
        boolean hasPresident = request.members().stream()
                .anyMatch(memberInfo -> memberInfo.role() == ClubRole.PRESIDENT);

        if (!hasPresident) {
            throw new CustomException(ErrorCode.CLUB_MEMBER_PRESIDENT_REQUIRED);
        }
    }

    private void validateDuplicateUserInRequest(ClubMemberRequest request) {
        Set<Long> userIds = new HashSet<>();

        for (ClubMemberRequest.MemberInfo memberInfo : request.members()) {
            if (!userIds.add(memberInfo.userId())) {
                throw new CustomException(ErrorCode.CLUB_MEMBER_DUPLICATE_USER);
            }
        }
    }

    private void validateAlreadyRegistered(Club club, User user) {
        if (clubMemberRepository.existsByClubAndUser(club, user)) {
            throw new CustomException(ErrorCode.CLUB_MEMBER_ALREADY_REGISTERED);
        }
    }

    public List<ClubMemberListResponse> getClubMembers(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        return clubMemberRepository.findByClubAndClubJoinStatus(club, ClubJoinStatus.JOINED)
                .stream()
                .map(clubMember -> ClubMemberListResponse.builder()
                        .userId(clubMember.getUser().getUserId())
                        .name(clubMember.getUser().getName())
                        .profileImage(clubMember.getUser().getProfileImage())
                        .role(clubMember.getRole())
                        .build())
                .toList();
    }
}