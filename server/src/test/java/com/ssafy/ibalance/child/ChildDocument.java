package com.ssafy.ibalance.child;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

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

    public static final Snippet childIdPathField = pathParameters(
            parameterWithName("childId").attributes(required()).description("path 에 들어가는 자녀 PK 아이디")
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

    public static final  Snippet deletedChildResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제 자녀정보 기존 PK ID"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("삭제 자녀정보의 자녀 이름"),
            fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("삭제한 유저의 PK ID")
    );

    public static final Snippet getChildDetailResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.childDetailResponse.childId").type(JsonFieldType.NUMBER).description("자녀 PK 아이디"),
            fieldWithPath("data.childDetailResponse.imageUrl").type(JsonFieldType.STRING).description("자녀 이미지 Url"),
            fieldWithPath("data.childDetailResponse.name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data.childDetailResponse.birthDate").type(JsonFieldType.STRING).description("자녀 생년월일"),
            fieldWithPath("data.childDetailResponse.gender").type(JsonFieldType.STRING).description("자녀 성별"),
            fieldWithPath("data.childDetailResponse.height").type(JsonFieldType.NUMBER).description("자녀 키"),
            fieldWithPath("data.childDetailResponse.weight").type(JsonFieldType.NUMBER).description("자녀 몸무게"),
            fieldWithPath("data.childDetailResponse.lastUpdateDate").type(JsonFieldType.STRING).description("자녀 마지막 업데이트일"),
            fieldWithPath("data.dietList").type(JsonFieldType.ARRAY).description("오늘의 식단 리스트"),
            fieldWithPath("data.dietList[].dietId").type(JsonFieldType.NUMBER).description("오늘의 식단 아이디").optional(),
            fieldWithPath("data.dietList[].dietDate").type(JsonFieldType.NUMBER).description("오늘의 식단 날짜").optional(),
            fieldWithPath("data.dietList[].sequence").type(JsonFieldType.NUMBER).description("오늘의 식단 순서(1:아침, 2:점심, 3:저녁, 4:순서없음)").optional(),
            fieldWithPath("data.dietList[].dietMenuList[].menuId").type(JsonFieldType.NUMBER).description("오늘의 식단 메뉴 아이디").optional(),
            fieldWithPath("data.dietList[].dietMenuList[].menuName").type(JsonFieldType.NUMBER).description("오늘의 식단 메뉴 이름").optional(),
            fieldWithPath("data.dietList[].dietMenuList[].menuType").type(JsonFieldType.NUMBER).description("오늘의 식단 메뉴 타입(RICE, SOUP, MAIN, SUB)").optional()
    );
}
