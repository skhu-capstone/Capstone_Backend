package com.skhu.skhucapstone.auth.service;

import com.skhu.skhucapstone.auth.client.GoogleApiClient;
import com.skhu.skhucapstone.auth.client.GoogleUserInfo;
import com.skhu.skhucapstone.auth.dto.GoogleLoginRes;
import com.skhu.skhucapstone.common.jwt.JwtUtil;
import com.skhu.skhucapstone.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.skhu.skhucapstone.user.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleApiClient googleApiClient;
    private final JwtUtil jwtUtil;

    public GoogleLoginRes googleLogin(String googleAccessToken) {
        // TODO
        // Google API 토큰 검증, 사용자 조회
        GoogleUserInfo userInfo = googleApiClient.getUserInfo(googleAccessToken);

        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> registerNewUser(userInfo));

        // JWT 발급
        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        // 응답 반환
        return GoogleLoginRes.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .schoolEmail(user.getSchoolEmail())
                .isVerified(user.getIsVerified())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    // DB 유저 확인 후 있으면 로그인, 없으면 회원가입
    private User registerNewUser (GoogleUserInfo userInfo){
        return userRepository.save(
                User.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .profileImage(userInfo.getPicture())
                        .build()
            );
    }
}
