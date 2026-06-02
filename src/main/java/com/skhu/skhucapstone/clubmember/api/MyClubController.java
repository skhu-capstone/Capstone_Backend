package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.response.MyClubResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
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
public class MyClubController {

    private final ClubMemberService clubMemberService;

    @GetMapping("/clubs")
    public ResponseEntity<ApiResponse<List<MyClubResponse>>> getMyClubs(
            @AuthenticationPrincipal Long userId) {

        List<MyClubResponse> response = clubMemberService.getMyClubs(userId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MY_CLUB_LIST_GET_SUCCESS, response));
    }
}