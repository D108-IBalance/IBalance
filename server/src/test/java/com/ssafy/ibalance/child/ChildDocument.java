package com.ssafy.ibalance.child;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class ChildDocument {

    public static final Snippet registerChildRequestField = requestFields(
            fieldWithPath("name").type(JsonFieldType.STRING).attributes(required()).description("아이 이름"),
            fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(required()).description("생년월일"),
            fieldWithPath("gender").type(JsonFieldType.STRING).attributes(required()).description("성별"),
            fieldWithPath("height").type(JsonFieldType.NUMBER).attributes(required()).description("키"),
            fieldWithPath("weight").type(JsonFieldType.NUMBER).attributes(required()).description("몸무게"),
            fieldWithPath("imageUrl").type(JsonFieldType.VARIES).attributes(required()).description("아이 이미지 URL"),
            fieldWithPath("haveAllergies").type(JsonFieldType.ARRAY).attributes(required()).description("알러지정보")
    );

    public static final Snippet registerChildResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("자녀 PK ID"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data.birthDate").type(JsonFieldType.ARRAY).description("자녀 생일"),
            fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
            fieldWithPath("data.height").type(JsonFieldType.NUMBER).description("키"),
            fieldWithPath("data.weight").type(JsonFieldType.NUMBER).description("몸무게"),
            fieldWithPath("data.imageUrl").type(JsonFieldType.VARIES).description("자녀 이미지 URL"),
            fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("등록한 유저의 PK ID")
    );

    public static final Snippet findChildResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data[].childId").type(JsonFieldType.NUMBER).description("자녀 PK 아이디"),
            fieldWithPath("data[].name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data[].imageUrl").type(JsonFieldType.VARIES).description("이미지 URL"),
            fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("성별")
    );
}
