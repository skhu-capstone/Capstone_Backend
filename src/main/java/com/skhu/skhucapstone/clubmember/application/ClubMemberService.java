package com.skhu.skhucapstone.clubmember.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.api.dto.request.ClubMemberRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberResponse;
import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    public ClubMemberResponse registerMembers(Long clubId, ClubMemberRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 동아리를 찾을 수 없습니다. clubId = " + clubId));

        int registeredCount = 0;

        for (ClubMemberRequest.MemberInfo memberInfo : request.members()) {
            User user = userRepository.findById(memberInfo.userId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. userId = " + memberInfo.userId()));

            if (clubMemberRepository.existsByClubAndUser(club, user)) {
                continue;
            }

            ClubMember clubMember = ClubMember.builder()
                    .club(club)
                    .user(user)
                    .role(memberInfo.role())
                    .build();

            clubMemberRepository.save(clubMember);
            registeredCount++;
        }

        return new ClubMemberResponse(
                clubId,
                registeredCount,
                ClubJoinStatus.JOINED,
                LocalDateTime.now()
        );
    }
}