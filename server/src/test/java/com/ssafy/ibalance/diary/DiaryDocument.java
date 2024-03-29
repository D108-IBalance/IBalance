package com.ssafy.ibalance.diary;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class DiaryDocument {

    public static final Snippet yearMonthQueryField = queryParameters(
            parameterWithName("year").attributes(required()).description("조회할 연도"),
            parameterWithName("month").attributes(required()).description("조회할 월 (1월 ~ 12월)")
    );

    public static final Snippet dietIdPathField = pathParameters(
            parameterWithName("dietId").attributes(required()).description("path 에 들어가는 Diet 아이디")
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
            fieldWithPath("data[].menuList[].menuId").type(JsonFieldType.STRING).description("메뉴 아이디"),
            fieldWithPath("data[].menuList[].menuName").type(JsonFieldType.STRING).description("메뉴명"),
            fieldWithPath("data[].menuList[].menuType").type(JsonFieldType.STRING).description("메뉴 타입 (밥 : RICE, 국 : SOUP, 반찬 : SIDE)")
    );

    public static final Snippet diaryInfoResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.date").type(JsonFieldType.STRING).description("식단 추천일자"),
            fieldWithPath("data.menu[].menuId").type(JsonFieldType.STRING).description("추천받은 메뉴의 아이디"),
            fieldWithPath("data.menu[].menuName").type(JsonFieldType.STRING).description("추천받은 메뉴 이름"),
            fieldWithPath("data.menu[].menuMaterial").type(JsonFieldType.ARRAY).description("추천받은 메뉴 재료"),
            fieldWithPath("data.menu[].menuImgUrl").type(JsonFieldType.STRING).description("추천받은 메뉴 사진 url"),
            fieldWithPath("data.materials[].id").type(JsonFieldType.NUMBER).description("식재료 아이디"),
            fieldWithPath("data.materials[].picky").type(JsonFieldType.BOOLEAN).description("편식 여부"),
            fieldWithPath("data.materials[].material").type(JsonFieldType.STRING).description("식재료 이름")
    );
}
