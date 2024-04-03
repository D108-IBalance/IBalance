package com.ssafy.ibalance.common.util;

import com.ssafy.ibalance.diet.exception.NoMoreExistMenuException;
import com.ssafy.ibalance.common.exception.NotValidInfoOnFastAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

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

    private <T> T apiConnectionResult(Function<WebClient, WebClient.RequestHeadersUriSpec<?>> init, String uri,
                                      Object bodyValue, T resultType) {

        WebClient.RequestHeadersSpec<?> initMethod = init.apply(webClient)
                .uri(uri);

        if(initMethod instanceof WebClient.RequestBodySpec && bodyValue != null) {
            initMethod = ((WebClient.RequestBodySpec) initMethod).bodyValue(bodyValue);
        }

        return initMethod.retrieve()
                .bodyToMono((Class<T>) resultType.getClass())
                .onErrorResume(WebClientResponseException.class,
                        e -> (Mono<? extends T>) checkNegativeCodeOnWebClient(e))
                .block();
    }

    private Mono<?> checkNegativeCodeOnWebClient(WebClientResponseException e) {
        HttpStatusCode statusCode = e.getStatusCode();

        if(statusCode.isSameCodeAs(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)) {
            throw new NoMoreExistMenuException("Redis 추천 금지 메뉴 데이터 초기화 필요");
        }

        if(statusCode.is4xxClientError()) {
            throw new NotValidInfoOnFastAPIException("WebClient 에 잘못된 값이 입력되었습니다.");
        }

        throw new IllegalArgumentException(e.getMessage());
    }
}
