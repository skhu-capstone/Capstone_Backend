package com.skhu.skhucapstone.auth.service;

import com.skhu.skhucapstone.auth.client.GoogleApiClient;
import com.skhu.skhucapstone.auth.client.GoogleUserInfo;
import com.skhu.skhucapstone.auth.dto.GoogleLoginRes;
import com.skhu.skhucapstone.auth.entity.EmailVerification;
import com.skhu.skhucapstone.auth.repository.EmailVerificationRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.common.jwt.JwtUtil;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.skhu.skhucapstone.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleApiClient googleApiClient;
    private final JwtUtil jwtUtil;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

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

    // 최초 인증번호 발송
    @Transactional
    public void sendVerificationEmail(String schoolEmail, Long userId){
        // 학교 이메일 도메인 검증
        if(!schoolEmail.endsWith("@office.skhu.ac.kr")){
            throw new CustomException(ErrorCode.INVALID_SCHOOL_EMAIL);
        }

        // 이미 인증된 이메일인지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));
        if(user.getIsVerified()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        }

        // 기존 인증 요청 삭제 후 새로 생성
        emailVerificationRepository.deleteByEmail(schoolEmail);

        String code = generateCode();
        EmailVerification verification = EmailVerification.builder()
                .email(schoolEmail)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .createdAt(LocalDateTime.now())
                .build();
        emailVerificationRepository.save(verification);

        // 이메일 발송
        emailService.sendVerificationCode(schoolEmail, code);
    }

    // 이메일 검증
    @Transactional
    public void verifyEmail(String schoolEmail, String code, Long userId) {
        EmailVerification verification = emailVerificationRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.VERIFICATION_NOT_FOUND));

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.VERIFICATION_EXPIRED);
        }

        if (!verification.getCode().equals(code)) {
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        user.verifySchoolEmail(schoolEmail);  // dirty checking으로 자동 UPDATE

        emailVerificationRepository.deleteByEmail(schoolEmail);
    }

    // 인증번호 재발송
    @Transactional
    public void resendVerificationEmail(String schoolEmail, Long userId) {
        // 도메인 검증
        if (!schoolEmail.endsWith("@office.skhu.ac.kr")) {
            throw new CustomException(ErrorCode.INVALID_SCHOOL_EMAIL);
        }

        // 기존 인증 요청 확인
        EmailVerification verification = emailVerificationRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.VERIFICATION_NOT_FOUND));

        // 1분 이내 재발송 시 에러
        if (verification.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS);
        }

        emailVerificationRepository.deleteByEmail(schoolEmail);

        String code = generateCode();
        EmailVerification newVerification = EmailVerification.builder()
                .email(schoolEmail)
                .code(code)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .createdAt(LocalDateTime.now())
                .build();
        emailVerificationRepository.save(newVerification);

        emailService.sendVerificationCode(schoolEmail, code);
    }

    private String generateCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000);
        return String.valueOf(code);
    }
}
