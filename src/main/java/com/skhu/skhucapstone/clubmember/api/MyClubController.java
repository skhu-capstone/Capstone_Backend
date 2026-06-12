package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.response.MyClubResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
@Tag(name = "My Club", description = "내 동아리 조회 API")
public class MyClubController {

    private final ClubMemberService clubMemberService;

    @GetMapping("/clubs")
    @Operation(
            summary = "내 소속 동아리 조회",
            description = "로그인한 사용자가 가입한 동아리 목록과 각 동아리에서의 역할(MEMBER, STAFF, PRESIDENT)을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<MyClubResponse>>> getMyClubs(
            @AuthenticationPrincipal Long userId) {

        List<MyClubResponse> response = clubMemberService.getMyClubs(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.MY_CLUB_LIST_GET_SUCCESS,
                        response
                )
        );
    }
}