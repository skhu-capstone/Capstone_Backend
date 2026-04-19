package com.skhu.skhucapstone.auth.controller;

import com.skhu.skhucapstone.auth.dto.GoogleLoginReq;
import com.skhu.skhucapstone.auth.dto.GoogleLoginRes;
import com.skhu.skhucapstone.auth.service.AuthService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google/login")
    public ResponseEntity<ApiResponse<GoogleLoginRes>> googleLogin(
            @RequestBody
            @Valid
            GoogleLoginReq req) {
        GoogleLoginRes res = authService.googleLogin(req.getGoogleAccessToken());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, res));
    }
}
