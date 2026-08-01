package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.request.ClubJoinRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubJoinCancelResponse;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubJoinResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs/{clubId}/join")
@RequiredArgsConstructor
@Tag(name = "Club Join", description = "동아리 가입 신청 관리 API")
public class ClubJoinController {

    private final ClubMemberService clubMemberService;

    @PostMapping
    @Operation(
            summary = "동아리 가입 신청",
            description = "로그인한 사용자가 동아리에 가입을 신청합니다. 거절 또는 탈퇴 상태에서는 재신청할 수 있습니다."
    )
    public ResponseEntity<ApiResponse<ClubJoinResponse>> requestJoin(
            @PathVariable Long clubId,
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ClubJoinRequest request) {

        ClubJoinResponse response = clubMemberService.requestJoin(clubId, userId, request);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CLUB_JOIN_REQUEST_SUCCESS, response));
    }

    @DeleteMapping("/me")
    @Operation(
            summary = "동아리 가입 신청 취소",
            description = "로그인한 사용자가 가입 대기 중인 자신의 동아리 가입 신청을 취소합니다."
    )
    public ResponseEntity<ApiResponse<ClubJoinCancelResponse>> cancelJoin(
            @PathVariable Long clubId,
            @AuthenticationPrincipal Long userId) {

        ClubJoinCancelResponse response = clubMemberService.cancelJoin(clubId, userId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CLUB_JOIN_CANCEL_SUCCESS, response));
    }
}