package com.skhu.skhucapstone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN_SUCCESS", "구글 소셜 로그인이 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
