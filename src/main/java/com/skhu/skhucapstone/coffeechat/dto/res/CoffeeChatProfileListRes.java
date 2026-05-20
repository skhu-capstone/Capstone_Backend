package com.skhu.skhucapstone.coffeechat.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class CoffeeChatProfileListRes {

    private List<CoffeeChatProfileListItemRes> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static CoffeeChatProfileListRes from(Page<CoffeeChatProfileListItemRes> pageData) {
        return CoffeeChatProfileListRes.builder()
                .content(pageData.getContent())
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .build();
    }
}
