package com.skhu.skhucapstone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN_SUCCESS", "구글 소셜 로그인이 완료되었습니다."),

    // 인증번호
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "EMAIL_SEND_SUCCESS", "인증번호가 발송되었습니다."),
    EMAIL_VERIFY_SUCCESS(HttpStatus.OK, "EMAIL_VERIFY_SUCCESS", "학교 이메일 인증이 완료되었습니다."),
    EMAIL_RESEND_SUCCESS(HttpStatus.OK, "EMAIL_RESEND_SUCCESS", "인증번호가 재발송되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
