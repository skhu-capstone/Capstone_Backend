package com.skhu.skhucapstone.auth.service;

import com.skhu.skhucapstone.auth.dto.GoogleLoginRes;
import com.skhu.skhucapstone.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public GoogleLoginRes googleLogin(String googleAccessToken){
        // TODO
        // Google API 토큰 검증, 사용자 조회
        // DB 유저 확인 후 있으면 로그인, 없으면 회원가입
        // JWT 발급
        // 응답 반환
        return null;
    }
}
