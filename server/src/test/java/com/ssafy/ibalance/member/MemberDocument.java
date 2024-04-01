package com.ssafy.ibalance.member;


import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class MemberDocument {

    public static final Snippet providerPathField = pathParameters(
            parameterWithName("provider").attributes(required()).description("path 에 들어가는 provider(google, naver, kakao")
    );

    public static final Snippet loginRequestField = requestFields(
            fieldWithPath("code").type(JsonFieldType.STRING).attributes(required()).description("OAuth 공급자로부터 오는 code"),
            fieldWithPath("url").type(JsonFieldType.STRING).attributes(required()).description("redirect url")
    );

    public static final Snippet loginResultResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("JWT 액세스 토큰"),
            fieldWithPath("data.tokenType").type(JsonFieldType.STRING).description("JWT 토큰 타입"),
            fieldWithPath("data.oAuthProvider").type(JsonFieldType.STRING).description("OAuth 제공자")
    );
}
