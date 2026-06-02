package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.request.ClubMemberRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberListResponse;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs/{clubId}/members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping
    public ClubMemberResponse registerMembers(@PathVariable Long clubId, @Valid @RequestBody ClubMemberRequest request) {
        return clubMemberService.registerMembers(clubId, request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClubMemberListResponse>>> getClubMembers(@PathVariable Long clubId) {
        List<ClubMemberListResponse> response = clubMemberService.getClubMembers(clubId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CLUB_MEMBER_LIST_GET_SUCCESS, response));
    }
}