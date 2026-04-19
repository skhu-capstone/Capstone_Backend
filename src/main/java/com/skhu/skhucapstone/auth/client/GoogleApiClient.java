package com.skhu.skhucapstone.auth.client;

import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GoogleApiClient {

    private final WebClient webClient = WebClient.create();

    public GoogleUserInfo getUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .header("Authorization", "Bearer " + accessToken) // 프론트에서 받은 토큰을 헤더에 담아 구글에 전달
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new CustomException(ErrorCode.INVALID_GOOGLE_TOKEN)))
                .bodyToMono(GoogleUserInfo.class)
                .block();
    }
}
