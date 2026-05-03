package com.skhu.skhucapstone.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailVerifyReq {

    @NotBlank(message = "학교 이메일을 입력해주세요.")
    private String schoolEmail;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private String code;
}
