package com.ssafy.ibalance.child;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

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

    public static final Snippet deletedChildResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제 자녀정보 기존 PK ID"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("삭제 자녀정보의 자녀 이름"),
            fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("삭제한 유저의 PK ID")
    );

    public static final Snippet getMainResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.childMainResponse.childId").type(JsonFieldType.NUMBER).description("자녀 PK 아이디"),
            fieldWithPath("data.childMainResponse.imageUrl").type(JsonFieldType.STRING).description("자녀 이미지 Url"),
            fieldWithPath("data.childMainResponse.name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data.childMainResponse.birthDate").type(JsonFieldType.STRING).description("자녀 생년월일"),
            fieldWithPath("data.childMainResponse.gender").type(JsonFieldType.STRING).description("자녀 성별"),
            fieldWithPath("data.childMainResponse.height").type(JsonFieldType.NUMBER).description("자녀 키"),
            fieldWithPath("data.childMainResponse.weight").type(JsonFieldType.NUMBER).description("자녀 몸무게"),
            fieldWithPath("data.childMainResponse.lastUpdateDate").type(JsonFieldType.STRING).description("자녀 마지막 업데이트일"),
            fieldWithPath("data.dietList").type(JsonFieldType.ARRAY).description("오늘의 식단 리스트"),
            fieldWithPath("data.dietList[].dietId").type(JsonFieldType.NUMBER).description("오늘의 식단 아이디").optional(),
            fieldWithPath("data.dietList[].dietDate").type(JsonFieldType.STRING).description("오늘의 식단 날짜").optional(),
            fieldWithPath("data.dietList[].sequence").type(JsonFieldType.NUMBER).description("오늘의 식단 순서(1:아침, 2:점심, 3:저녁, 4:순서없음)").optional(),
            fieldWithPath("data.dietList[].menuList[].menuId").type(JsonFieldType.STRING).description("오늘의 식단 메뉴 아이디").optional(),
            fieldWithPath("data.dietList[].menuList[].menuName").type(JsonFieldType.STRING).description("오늘의 식단 메뉴 이름").optional(),
            fieldWithPath("data.dietList[].menuList[].menuType").type(JsonFieldType.STRING).description("오늘의 식단 메뉴 타입(RICE, SOUP, MAIN, SUB)").optional()
    );

    public static final Snippet pageableQueryField = queryParameters(
            parameterWithName("page").attributes(required()).description("조회할 페이지 (0부터 시작)"),
            parameterWithName("size").attributes(required()).description("조회할 데이터 수 (4)")
    );

    public static final Snippet getGrowthListResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
            fieldWithPath("data.growthList[].gender").type(JsonFieldType.STRING).description("자녀 성별"),
            fieldWithPath("data.growthList[].birthDate").type(JsonFieldType.STRING).description("자녀 생년월일"),
            fieldWithPath("data.growthList[].month").type(JsonFieldType.NUMBER).description("자녀 개월 수"),
            fieldWithPath("data.growthList[].recordDate").type(JsonFieldType.STRING).description("자녀 정보 기록일"),
            fieldWithPath("data.growthList[].startDate").type(JsonFieldType.STRING).description("기록일 기준 일주일의 일요일"),
            fieldWithPath("data.growthList[].endDate").type(JsonFieldType.STRING).description("기록일 기준 일주일의 토요일"),
            fieldWithPath("data.growthList[].height").type(JsonFieldType.NUMBER).description("자녀 키"),
            fieldWithPath("data.growthList[].weight").type(JsonFieldType.NUMBER).description("자녀 몸무게"),
            fieldWithPath("data.averageList").type(JsonFieldType.ARRAY).description("평균 성장 데이터"),
            fieldWithPath("data.averageList[].month").type(JsonFieldType.NUMBER).description("개월 수").optional(),
            fieldWithPath("data.averageList[].averageHeight").type(JsonFieldType.NUMBER).description("평균 키").optional(),
            fieldWithPath("data.averageList[].averageWeight").type(JsonFieldType.NUMBER).description("평균 몸무게").optional()
    );

    public static final Snippet changeProfileImageResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.childId").type(JsonFieldType.NUMBER).description("자녀 PK ID"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data.imageUrl").type(JsonFieldType.STRING).description("자녀 이미지 URL"),
            fieldWithPath("data.gender").type(JsonFieldType.STRING).description("자녀 성별")
    );

    public static final Snippet childDetailResponseField = responseFields(
            fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.childId").type(JsonFieldType.NUMBER).description("자녀 PK 아이디"),
            fieldWithPath("data.imageUrl").type(JsonFieldType.STRING).description("자녀 이미지 Url"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("자녀 이름"),
            fieldWithPath("data.birthDate").type(JsonFieldType.STRING).description("자녀 생년월일"),
            fieldWithPath("data.gender").type(JsonFieldType.STRING).description("자녀 성별"),
            fieldWithPath("data.height").type(JsonFieldType.NUMBER).description("자녀 키"),
            fieldWithPath("data.weight").type(JsonFieldType.NUMBER).description("자녀 몸무게"),
            fieldWithPath("data.allergies").type(JsonFieldType.ARRAY).description("자녀 알러지 아이디 목록")
    );
}
