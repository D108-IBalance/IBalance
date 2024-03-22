package com.ssafy.ibalance.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FastAPIConnectionUtil {

    private static final String apiUrl = "https://j10d108.p.ssafy.io/recomm";

    private final WebClient webClient;

    public <T> T getApiConnectionResult(String uri, T resultType) {
        return apiConnectionResult(WebClient::get, apiUrl + uri, null, resultType);
    }

    public <T> T postApiConnectionResult(String uri, Object bodyValue, T resultType) {
        return apiConnectionResult(WebClient::post, apiUrl + uri, bodyValue, resultType);
    }

    public <T> T apiConnectionResult(Function<WebClient, WebClient.RequestHeadersUriSpec<?>> init, String uri,
                                     Object bodyValue, T resultType) {

        WebClient.RequestHeadersSpec<?> initMethod = init.apply(webClient)
                .uri(uri);

        if(initMethod instanceof WebClient.RequestBodySpec && bodyValue != null) {
            initMethod = ((WebClient.RequestBodySpec) initMethod).bodyValue(bodyValue);
        }

        return initMethod.retrieve()
                .bodyToMono((Class<T>) resultType.getClass())
                .onErrorMap(e -> new IllegalArgumentException("FastAPIConnection Error"))
                .block();
    }
}
