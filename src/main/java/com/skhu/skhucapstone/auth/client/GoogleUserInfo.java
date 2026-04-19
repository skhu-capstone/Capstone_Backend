package com.skhu.skhucapstone.auth.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 구글 응답 중 내가 필요한 것만 가져옴
public class GoogleUserInfo {

    private String email;
    private String name;
    private String picture;
}
