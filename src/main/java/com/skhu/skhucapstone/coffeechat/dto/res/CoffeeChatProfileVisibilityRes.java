package com.skhu.skhucapstone.coffeechat.dto.res;

import com.skhu.skhucapstone.coffeechat.entity.CoffeeChatProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeChatProfileVisibilityRes {

    private Long coffeeChatProfileId;
    private Boolean isPublic;

    public static CoffeeChatProfileVisibilityRes from(CoffeeChatProfile profile) {
        return CoffeeChatProfileVisibilityRes.builder()
                .coffeeChatProfileId(profile.getId())
                .isPublic(profile.getIsPublic())
                .build();
    }
}
