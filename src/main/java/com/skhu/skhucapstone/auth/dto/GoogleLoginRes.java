package com.skhu.skhucapstone.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleLoginRes {

    private Long userId;
    private String email;
    private String name;
    private String profileImage;
    private String schoolEmail;
    private Boolean isVerified;
    private String accessToken;
    private String refreshToken;
}
