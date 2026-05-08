package com.skhu.skhucapstone.coffeechat.dto.res;

import com.skhu.skhucapstone.coffeechat.entity.CoffeeChatProfile;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CoffeeChatUserProfileRes {

    private Long userId;
    private String name;
    private List<String> clubs;
    private CoffeeChatProfileRes coffeeChatProfile;

    public static CoffeeChatUserProfileRes of(CoffeeChatProfile profile, List<String> clubs) {
        return CoffeeChatUserProfileRes.builder()
                .userId(profile.getUser().getUserId())
                .name(profile.getUser().getName())
                .clubs(clubs)
                .coffeeChatProfile(CoffeeChatProfileRes.from(profile))
                .build();
    }
}
