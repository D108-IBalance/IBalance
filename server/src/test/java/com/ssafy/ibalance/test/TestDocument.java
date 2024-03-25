package com.ssafy.ibalance.test;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;


public class TestDocument {

    public static final Snippet testPathField = pathParameters(
            parameterWithName("name").attributes(required()).description("path 에 들어가는 이름")
    );

    public static final Snippet queryDslResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.[].entityId").type(JsonFieldType.NUMBER).description("테스트 엔티티 ID"),
            fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.[].address").type(JsonFieldType.STRING).description("주소")
    );
}
