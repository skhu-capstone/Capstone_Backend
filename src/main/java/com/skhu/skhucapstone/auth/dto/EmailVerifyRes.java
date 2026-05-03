package com.skhu.skhucapstone.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailVerifyRes {

    private Long userId;
    private String schoolEmail;
    private Boolean isVerified;
}

