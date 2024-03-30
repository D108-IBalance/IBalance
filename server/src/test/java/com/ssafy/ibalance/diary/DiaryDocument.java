package com.ssafy.ibalance.diary;

import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class DiaryDocument {

    public static final Snippet yearMonthQueryField = queryParameters(
            parameterWithName("year").attributes(required()).description("조회할 연도"),
            parameterWithName("month").attributes(required()).description("조회할 월 (1월 ~ 12월)")
    );

    public static final Snippet dietIdPathField = pathParameters(
            parameterWithName("dietId").attributes(required()).description("path 에 들어가는 Diet 아이디")
    );

    public static final Snippet childAndDietIdPathField = pathParameters(
            parameterWithName("childId").attributes(required()).description("path 에 들어가는 Child 아이디"),
            parameterWithName("dietId").attributes(required()).description("path 에 들어가는 Diet 아이디")

    );

    public static final Snippet saveDiaryRequestField = requestFields(
            fieldWithPath("dietId").attributes(required()).description("body 에 들어가는 diet ID"),
            fieldWithPath("content").attributes(required()).description("식단 일기 글 내용"),
            fieldWithPath("menuRate[].menuId").attributes(required()).description("별점 데이터 내의 메뉴 아이디"),
            fieldWithPath("menuRate[].rate").attributes(required()).description("1~5점 내의 별점 데이터"),
            fieldWithPath("pickyIdList").attributes(required()).description("편식한 아이디 리스트"),
            fieldWithPath("mealTime").type(VARIES).description("식사 시간(BREAKFAST, LUNCH, DINNER, NONE)").optional()
    );

    public static final Snippet getCalendarListResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data").type(ARRAY).description("식단 날짜 배열"),
            fieldWithPath("data[].dietDate").type(STRING).description("식단이 존재하는 날짜").optional(),
            fieldWithPath("data[].allReviewed").type(BOOLEAN).description("모든 식단의 리뷰 여부").optional()
    );

    public static final Snippet getDietByDateResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data[].dietId").type(NUMBER).description("식단 아이디").optional(),
            fieldWithPath("data[].dietDate").type(STRING).description("식단 날짜").optional(),
            fieldWithPath("data[].mealTime").type(STRING).description("아침/점심/저녁/해당없음 표기").optional(),
            fieldWithPath("data[].menuList[].menuId").type(STRING).description("메뉴 아이디"),
            fieldWithPath("data[].menuList[].menuName").type(STRING).description("메뉴명"),
            fieldWithPath("data[].menuList[].menuType").type(STRING).description("메뉴 타입 (밥 : RICE, 국 : SOUP, 반찬 : SIDE)")
    );

    public static final Snippet diaryInfoResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.date").type(STRING).description("식단 추천일자"),
            fieldWithPath("data.menu[].menuId").type(STRING).description("추천받은 메뉴의 아이디"),
            fieldWithPath("data.menu[].menuName").type(STRING).description("추천받은 메뉴 이름"),
            fieldWithPath("data.menu[].materials").type(VARIES).description("추천받은 메뉴 재료"),
            fieldWithPath("data.menu[].menuImgUrl").type(STRING).description("추천받은 메뉴 사진 url"),
            fieldWithPath("data.materials[].id").type(NUMBER).description("식재료 아이디"),
            fieldWithPath("data.materials[].picky").type(BOOLEAN).description("편식 여부"),
            fieldWithPath("data.materials[].material").type(STRING).description("식재료 이름")
    );

    public static final Snippet diarySaveResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.dietId").type(NUMBER).description("저장된 식단 아이디"),
            fieldWithPath("data.date").type(STRING).description("식단이 생성된 날짜"),
            fieldWithPath("data.content").type(STRING).description("식단 일기 내용"),
            fieldWithPath("data.mealTime").type(STRING).description("식사 시간"),
            fieldWithPath("data.materials[].id").type(NUMBER).description("식단 식재료 아이디"),
            fieldWithPath("data.materials[].material").type(STRING).description("식단 식재료 이름"),
            fieldWithPath("data.materials[].picky").type(BOOLEAN).description("식단 식재료 편식여부")
    );

    public static final Snippet writtenDiaryResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.date").type(STRING).description("음식을 추천받은 날짜"),
            fieldWithPath("data.content").type(STRING).description("식단 일기 내용"),
            fieldWithPath("data.mealTime").type(STRING).description("일기에 기록된 식사 시간"),
            fieldWithPath("data.diaryMenuList[].menuId").type(STRING).description("식사 메뉴 고유 아이디"),
            fieldWithPath("data.diaryMenuList[].menuName").type(STRING).description("식사 메뉴 이름"),
            fieldWithPath("data.diaryMenuList[].materials").type(ARRAY).description("식사 메뉴에 들어가는 식재료"),
            fieldWithPath("data.diaryMenuList[].menuImgUrl").type(STRING).description("식사 메뉴 사진"),
            fieldWithPath("data.materials[].id").type(NUMBER).description("식재료 고유 아이디"),
            fieldWithPath("data.materials[].picky").type(BOOLEAN).description("식재료 편식 여부"),
            fieldWithPath("data.materials[].material").type(STRING).description("식단 식재료 이름")
    );
}
