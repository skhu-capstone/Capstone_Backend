package com.skhu.skhucapstone.club.api;

import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.application.ClubService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/clubs")
@RequiredArgsConstructor
@Tag(name = "Admin Club", description = "관리자 동아리 관리 API")
public class AdminClubController {

    private final ClubService clubService;

    @GetMapping
    @Operation(
            summary = "승인 대기 동아리 목록 조회",
            description = "관리자가 승인되지 않은 동아리 신청 목록을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ClubResponse>>> getUnapprovedClubs() {

        List<ClubResponse> response = clubService.getUnapprovedClubs();

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_PENDING_LIST_GET_SUCCESS,
                        response
                )
        );
    }
}