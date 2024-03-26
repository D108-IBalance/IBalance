package com.ssafy.ibalance.diary;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

public class DiaryDocument {

    public static final Snippet yearMonthQueryField = queryParameters(
            parameterWithName("year").attributes(required()).description("조회할 연도"),
            parameterWithName("month").attributes(required()).description("조회할 월 (1월 ~ 12월)")
    );

    public static final Snippet getCalendarListResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data").type(JsonFieldType.ARRAY).description("식단 날짜 배열"),
            fieldWithPath("data[].dietDate").type(JsonFieldType.STRING).description("식단이 존재하는 날짜").optional(),
            fieldWithPath("data[].allReviewed").type(JsonFieldType.BOOLEAN).description("모든 식단의 리뷰 여부").optional()
    );

    public static final Snippet getDietByDateResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data[].dietId").type(JsonFieldType.NUMBER).description("식단 아이디").optional(),
            fieldWithPath("data[].dietDate").type(JsonFieldType.STRING).description("식단 날짜").optional(),
            fieldWithPath("data[].sequence").type(JsonFieldType.NUMBER).description("식단 순서 (아침 : 1, 점심 : 2, 저녁 : 3, 순서 없음 : 4)").optional(),
            fieldWithPath("data[].menuList[].menuId").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
            fieldWithPath("data[].menuList[].menuName").type(JsonFieldType.STRING).description("메뉴명"),
            fieldWithPath("data[].menuList[].menuType").type(JsonFieldType.STRING).description("메뉴 타입 (밥 : RICE, 국 : SOUP, 반찬 : SIDE)")
    );
}
