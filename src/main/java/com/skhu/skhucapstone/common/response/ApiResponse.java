package com.skhu.skhucapstone.common.response;

import com.skhu.skhucapstone.common.exception.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data){
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}
