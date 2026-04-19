package com.skhu.skhucapstone.auth.controller;

import com.skhu.skhucapstone.auth.dto.GoogleLoginReq;
import com.skhu.skhucapstone.auth.dto.GoogleLoginRes;
import com.skhu.skhucapstone.auth.service.AuthService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인/회원가입 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google/login")
    @Operation(summary = "구글 소셜 로그인", description = "구글 액세스 토큰으로 로그인 또는 회원가입을 처리합니다.")
    public ResponseEntity<ApiResponse<GoogleLoginRes>> googleLogin(
            @RequestBody
            @Valid
            GoogleLoginReq req) {
        GoogleLoginRes res = authService.googleLogin(req.getGoogleAccessToken());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, res));
    }
}
