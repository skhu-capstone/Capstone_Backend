package com.skhu.skhucapstone.mypage.controller;

import com.skhu.skhucapstone.coffeechat.dto.req.CoffeeChatProfileSaveReq;
import com.skhu.skhucapstone.coffeechat.dto.req.CoffeeChatProfileVisibilityReq;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileVisibilityRes;
import com.skhu.skhucapstone.coffeechat.service.CoffeeChatService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import com.skhu.skhucapstone.mypage.dto.MypageRes;
import com.skhu.skhucapstone.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Tag(name = "Mypage", description = "마이페이지 API")
public class MypageController {

    private final MypageService mypageService;
    private final CoffeeChatService coffeeChatService;

    @GetMapping
    @Operation(summary = "마이페이지 조회", description = "내 프로필과 커피챗 프로필을 조회합니다.")
    public ResponseEntity<ApiResponse<MypageRes>> getMypage(
            @AuthenticationPrincipal Long userId) {
        MypageRes res = mypageService.getMypage(userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MYPAGE_GET_SUCCESS, res));
    }

    @PutMapping("/coffeechat-profile")
    @Operation(summary = "커피챗 프로필 저장", description = "커피챗 프로필을 저장합니다. 없으면 생성, 있으면 수정합니다.")
    public ResponseEntity<ApiResponse<CoffeeChatProfileRes>> saveCoffeeChatProfile(
            @RequestBody CoffeeChatProfileSaveReq req,
            @AuthenticationPrincipal Long userId) {
        CoffeeChatProfileRes res = coffeeChatService.saveProfile(userId, req);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_SAVE_SUCCESS, res));
    }

    @PatchMapping("/coffeechat-profile/visibility")
    @Operation(summary = "커피챗 프로필 공개여부 변경", description = "커피챗 프로필 공개여부를 변경합니다.")
    public ResponseEntity<ApiResponse<CoffeeChatProfileVisibilityRes>> updateVisibility(
            @RequestBody CoffeeChatProfileVisibilityReq req,
            @AuthenticationPrincipal Long userId) {
        CoffeeChatProfileVisibilityRes res = coffeeChatService.updateVisibility(userId, req);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_VISIBILITY_UPDATE_SUCCESS, res));
    }
}
