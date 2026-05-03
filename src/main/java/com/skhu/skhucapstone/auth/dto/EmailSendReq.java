package com.skhu.skhucapstone.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailSendReq {

    @NotBlank(message = "학교 이메일을 입력해주세요.")
    private String schoolEmail;
}
