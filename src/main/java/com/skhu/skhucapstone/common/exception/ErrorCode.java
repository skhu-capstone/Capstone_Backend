package com.skhu.skhucapstone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Google 로그인
    INVALID_GOOGLE_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_GOOGLE_TOKEN", "유효하지 않은 구글 토큰입니다."),
    FORBIDDEN_LOGIN(HttpStatus.FORBIDDEN, "FORBIDDEN_LOGIN", "허용되지 않은 로그인 요청입니다."),
    EMAIL_ALREADY_LINKED(HttpStatus.CONFLICT, "EMAIL_ALREADY_LINKED", "이미 다른 계정으로 가입된 이메일입니다."),

    // 학교 이메일
    INVALID_SCHOOL_EMAIL(HttpStatus.FORBIDDEN, "INVALID_SCHOOL_EMAIL", "학교 이메일 형식이 아닙니다."),
    ALREADY_VERIFIED_EMAIL(HttpStatus.CONFLICT, "ALREADY_VERIFIED_EMAIL", "이미 인증된 학교 이메일입니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_SEND_FAILED", "인증번호 발송에 실패했습니다."),

    // 인증코드
    INVALID_VERIFICATION_CODE(HttpStatus.FORBIDDEN, "INVALID_VERIFICATION_CODE", "인증번호가 일치하지 않습니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "VERIFICATION_NOT_FOUND", "인증 요청 정보를 찾을 수 없습니다."),
    VERIFICATION_EXPIRED(HttpStatus.GONE, "VERIFICATION_EXPIRED", "인증번호가 만료되었습니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_REQUESTS", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

    // 공통
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "로그인이 필요합니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "올바르지 않은 요청입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
