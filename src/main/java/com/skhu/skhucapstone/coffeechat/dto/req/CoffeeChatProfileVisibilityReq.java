package com.skhu.skhucapstone.coffeechat.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoffeeChatProfileVisibilityReq {

    @NotNull(message = "공개 여부를 선택해주세요.")
    private Boolean isPublic;
}
