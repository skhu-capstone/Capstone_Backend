package com.skhu.skhucapstone.chat.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChatMessageSendReq {

    // 메시지 내용 (빈 값 불가)
    @NotBlank(message = "메시지 내용을 입력해주세요.")
    private String content;
}
