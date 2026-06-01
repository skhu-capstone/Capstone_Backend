package com.skhu.skhucapstone.chat.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomListRes {

    private List<ChatRoomListItemRes> content;
}
