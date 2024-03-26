package com.ssafy.ibalance.common;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

public class CommonDocument {

    public static final Snippet AccessTokenHeader = requestHeaders(
            headerWithName("Authorization").attributes(required()).description("access token")
    );

    public static final Snippet DateQueryField = queryParameters(
            parameterWithName("date").attributes(required()).description("조회할 날짜 (yyyy-MM-dd)")
    );
}
