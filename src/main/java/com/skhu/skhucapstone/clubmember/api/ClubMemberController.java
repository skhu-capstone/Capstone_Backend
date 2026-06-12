package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.request.ClubMemberRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberListResponse;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs/{clubId}/members")
@RequiredArgsConstructor
@Tag(name = "Club Member", description = "동아리 멤버 관리 API")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping
    @Operation(
            summary = "동아리 멤버 등록",
            description = "동아리 부원 명단을 등록합니다. MEMBER, STAFF, PRESIDENT 역할을 지정할 수 있습니다."
    )
    public ResponseEntity<ApiResponse<ClubMemberResponse>> registerMembers(
            @PathVariable Long clubId,
            @Valid @RequestBody ClubMemberRequest request) {

        ClubMemberResponse response =
                clubMemberService.registerMembers(clubId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_MEMBER_REGISTER_SUCCESS,
                        response
                )
        );
    }

    @GetMapping
    @Operation(
            summary = "동아리 멤버 목록 조회",
            description = "특정 동아리에 등록된 멤버 목록과 역할 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ClubMemberListResponse>>> getClubMembers(
            @PathVariable Long clubId) {

        List<ClubMemberListResponse> response =
                clubMemberService.getClubMembers(clubId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_MEMBER_LIST_GET_SUCCESS,
                        response
                )
        );
    }
}