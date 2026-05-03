package com.skhu.skhucapstone.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    // 에러코드를 받아서 거기서 메시지, 상태코드를 꺼내 씀
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
