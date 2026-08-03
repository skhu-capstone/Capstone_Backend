package com.skhu.skhucapstone.coffeechat.controller;

import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatProfileListRes;
import com.skhu.skhucapstone.coffeechat.dto.res.CoffeeChatUserProfileRes;
import com.skhu.skhucapstone.coffeechat.service.CoffeeChatService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/coffeechat")
@RequiredArgsConstructor
@Tag(name = "CoffeeChat", description = "커피챗 프로필 API")
public class CoffeeChatController {

    private final CoffeeChatService coffeeChatService;

    @GetMapping("/profiles/{userId}")
    @Operation(summary = "커피챗 프로필 단건 조회", description = "특정 유저의 커피챗 프로필을 조회합니다. 비공개 프로필은 조회할 수 없습니다.")
    public ResponseEntity<ApiResponse<CoffeeChatUserProfileRes>> getUserProfile(
            @PathVariable Long userId) {
        CoffeeChatUserProfileRes res = coffeeChatService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_GET_SUCCESS, res));
    }

    @GetMapping("/profiles")
    @Operation(summary = "커피챗 프로필 목록 조회", description = "공개된 커피챗 프로필 목록을 조회합니다. 키워드로 검색할 수 있습니다.")
    public ResponseEntity<ApiResponse<CoffeeChatProfileListRes>> getProfiles(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        CoffeeChatProfileListRes res = coffeeChatService.getProfiles(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_LIST_GET_SUCCESS, res));
    }

    @PostMapping(value = "/profiles/{userId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "커피챗 프로필 이미지 업로드", description = "본인의 커피챗 프로필 이미지를 업로드합니다. 다른 사용자의 이미지는 변경할 수 없습니다.")
    public ResponseEntity<ApiResponse<String>> uploadProfileImage(
            @AuthenticationPrincipal Long loginUserId,
            @PathVariable Long userId,
            @RequestPart MultipartFile file) {
        String imageUrl = coffeeChatService.uploadProfileImage(loginUserId, userId, file);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_IMAGE_UPLOAD_SUCCESS, imageUrl));
    }

    @DeleteMapping("/profiles/{userId}/image")
    @Operation(summary = "커피챗 프로필 이미지 삭제", description = "본인의 커피챗 프로필 이미지를 삭제합니다. 이미지가 없어도 성공으로 처리됩니다.")
    public ResponseEntity<ApiResponse<Void>> deleteProfileImage(
            @AuthenticationPrincipal Long loginUserId,
            @PathVariable Long userId) {
        coffeeChatService.deleteProfileImage(loginUserId, userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COFFEECHAT_PROFILE_IMAGE_DELETE_SUCCESS, null));
    }
}
