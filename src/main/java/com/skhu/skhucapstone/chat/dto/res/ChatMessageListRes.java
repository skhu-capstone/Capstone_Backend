package com.skhu.skhucapstone.chat.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class ChatMessageListRes {

    private List<ChatMessageRes> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static ChatMessageListRes from(Page<ChatMessageRes> pageData) {
        return ChatMessageListRes.builder()
                .content(pageData.getContent())
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .build();
    }
}
