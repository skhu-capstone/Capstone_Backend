package com.skhu.skhucapstone.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleLoginReq {

    @NotBlank(message = "구글 액세스 토큰을 입력해주세요.") // 의존성 추가
    private String googleAccessToken;
}
