package com.skhu.skhucapstone.coffeechat.service;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.coffeechat.dto.req.CoffeeChatProfileSaveReq;
import com.skhu.skhucapstone.coffeechat.dto.req.CoffeeChatProfileVisibilityReq;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileListItemRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileListRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileVisibilityRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatUserProfileRes;
import com.skhu.skhucapstone.coffeechat.entity.CoffeeChatProfile;
import com.skhu.skhucapstone.coffeechat.repository.CoffeeChatProfileRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoffeeChatService {

    private final CoffeeChatProfileRepository coffeeChatProfileRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    // 커피챗 프로필 저장 (없으면 생성, 있으면 수정)
    @Transactional
    public CoffeeChatProfileRes saveProfile(Long userId, CoffeeChatProfileSaveReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        CoffeeChatProfile profile = coffeeChatProfileRepository.findByUserUserId(userId)
                .map(existing -> {
                    // null인 필드는 기존 값 유지
                    existing.update(
                            req.getStudentId() != null ? req.getStudentId() : existing.getStudentId(),
                            req.getHeadline() != null ? req.getHeadline() : existing.getHeadline(),
                            req.getInterestTopics() != null ? req.getInterestTopics() : existing.getInterestTopics(),
                            req.getMeetingType() != null ? req.getMeetingType() : existing.getMeetingType(),
                            req.getContactLink() != null ? req.getContactLink() : existing.getContactLink(),
                            req.getIntroduction() != null ? req.getIntroduction() : existing.getIntroduction(),
                            existing.getIsPublic()
                    );
                    return existing;
                })
                // 프로필 없으면 새로 생성
                .orElseGet(() -> coffeeChatProfileRepository.save(
                        CoffeeChatProfile.builder()
                                .user(user)
                                .studentId(req.getStudentId())
                                .headline(req.getHeadline())
                                .interestTopics(req.getInterestTopics())
                                .meetingType(req.getMeetingType())
                                .contactLink(req.getContactLink())
                                .introduction(req.getIntroduction())
                                .build()
                ));

        return CoffeeChatProfileRes.from(profile);
    }

    // 커피챗 프로필 공개여부 변경
    @Transactional
    public CoffeeChatProfileVisibilityRes updateVisibility(Long userId, CoffeeChatProfileVisibilityReq req) {
        CoffeeChatProfile profile = coffeeChatProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.COFFEECHAT_PROFILE_NOT_FOUND));

        profile.updateVisibility(req.getIsPublic());

        return CoffeeChatProfileVisibilityRes.from(profile);
    }

    // 특정 유저 커피챗 프로필 조회 (비공개면 조회 불가)
    @Transactional(readOnly = true)
    public CoffeeChatUserProfileRes getUserProfile(Long targetUserId) {
        CoffeeChatProfile profile = coffeeChatProfileRepository.findByUserUserId(targetUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.COFFEECHAT_PROFILE_NOT_FOUND));

        // 비공개 프로필 접근 차단
        if (!profile.getIsPublic()) {
            throw new CustomException(ErrorCode.COFFEECHAT_PROFILE_PRIVATE);
        }

        List<String> clubs = getClubs(targetUserId);

        return CoffeeChatUserProfileRes.of(profile, clubs);
    }

    // 커피챗 프로필 목록 조회 (키워드 검색 + 페이징)
    @Transactional(readOnly = true)
    public CoffeeChatProfileListRes getProfiles(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // 빈 문자열이면 전체 조회
        String searchKeyword = (keyword == null || keyword.isBlank()) ? null : keyword;

        return CoffeeChatProfileListRes.from(
                coffeeChatProfileRepository.searchProfiles(searchKeyword, pageable)
                        .map(profile -> CoffeeChatProfileListItemRes.of(
                                profile,
                                getClubs(profile.getUser().getUserId())
                        ))
        );
    }

    // 유저의 동아리 목록 조회 (동아리명 / 역할)
    private List<String> getClubs(Long userId) {
        List<ClubMember> clubMembers = clubMemberRepository
                .findByUserUserIdAndClubJoinStatus(userId, ClubJoinStatus.JOINED);

        // 가입한 동아리 없으면 null
        if (clubMembers.isEmpty()) return null;

        return clubMembers.stream()
                .map(cm -> cm.getClub().getClubName() + " / " + cm.getRole().name())
                .collect(Collectors.toList());
    }
}
