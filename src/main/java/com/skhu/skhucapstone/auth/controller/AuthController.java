package com.skhu.skhucapstone.auth.controller;

import com.skhu.skhucapstone.auth.dto.*;
import com.skhu.skhucapstone.auth.service.AuthService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/email/send")
    @Operation(summary = "학교 이메일 인증번호 발송", description = "학교 이메일로 인증번호를 발송합니다.")
    public ResponseEntity<ApiResponse<EmailSendRes>> sendVerificationEmail(
            @RequestBody @Valid EmailSendReq req,
            @AuthenticationPrincipal Long userId
    ){
        authService.sendVerificationEmail(req.getSchoolEmail(), userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.EMAIL_SEND_SUCCESS, new EmailSendRes(req.getSchoolEmail())));
    }

    @PostMapping("/email/verify")
    @Operation(summary = "학교 이메일 인증번호 확인", description = "인증번호를 확인하고 학교 이메일 인증을 완료합니다.")
    public ResponseEntity<ApiResponse<EmailVerifyRes>> verifyEmail(
            @RequestBody @Valid EmailVerifyReq req,
            @AuthenticationPrincipal Long userId
    ) {
        authService.verifyEmail(req.getSchoolEmail(), req.getCode(), userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.EMAIL_VERIFY_SUCCESS, new EmailVerifyRes(userId, req.getSchoolEmail(), true)));
    }

    @PostMapping("/email/resend")
    @Operation(summary = "학교 이메일 인증번호 재발송", description = "인증번호를 재발송합니다.")
    public ResponseEntity<ApiResponse<EmailSendRes>> resendVerificationEmail(
            @RequestBody @Valid EmailSendReq req,
            @AuthenticationPrincipal Long userId) {
        authService.resendVerificationEmail(req.getSchoolEmail(), userId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.EMAIL_RESEND_SUCCESS, new EmailSendRes(req.getSchoolEmail())));
    }
}
