package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberListResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clubs/{clubId}/members")
@RequiredArgsConstructor
@Tag(name = "Club Member", description = "동아리 멤버 관리 API")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @GetMapping
    @Operation(
            summary = "동아리 멤버 목록 조회",
            description = "특정 동아리에 가입 완료된 멤버 목록과 역할 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ClubMemberListResponse>>> getClubMembers(
            @PathVariable Long clubId) {

        List<ClubMemberListResponse> response = clubMemberService.getClubMembers(clubId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CLUB_MEMBER_LIST_GET_SUCCESS, response));
    }
}