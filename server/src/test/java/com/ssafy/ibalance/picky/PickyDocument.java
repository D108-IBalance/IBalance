package com.ssafy.ibalance.picky;

import org.springframework.restdocs.snippet.Snippet;

import static com.ssafy.ibalance.common.DocumentFormatProvider.required;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class PickyDocument {

    public static final Snippet pickyPeriodRequestParam = queryParameters(
            parameterWithName("limit").attributes(required()).description("일주일/한달 단위로 편식 관련 기간 설정하는 field")
    );

    public static final Snippet pickyRecipeRequestParam = queryParameters(
            parameterWithName("material").attributes(required()).description("찾고자 하는 음식 재료 이름"),
            parameterWithName("offset").attributes(required()).description("받고자 하는 정보의 갯수"),
            parameterWithName("lastId").description("마지막에 받았던 recipe id").optional()
    );


    public static final Snippet onePickyRecipePathParam = pathParameters(
            parameterWithName("material").attributes(required()).description("조회하고자 하는 재료 이름"),
            parameterWithName("recipeId").attributes(required()).description("조회하고자 하는 레시피 아이디")
    );

    public static final Snippet pickyMainResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data[].pickyMaterialName").type(STRING).description("편식 식재료명"),
            fieldWithPath("data[].recipes[].recipeTitle").type(STRING).description("레시피제목"),
            fieldWithPath("data[].recipes[].recipeImgUrl").type(STRING).description("레시피 사진 URL"),
            fieldWithPath("data[].recipes[].recipeId").type(STRING).description("레시피 고유 ID")
    );

    public static final Snippet pickyRecipeResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data.recipeTitle").type(STRING).description("레시피 이름"),
            fieldWithPath("data.recipeImgUrl").type(STRING).description("레시피 이미지 URL"),
            fieldWithPath("data.recipeMaterialList[].materialName").type(STRING).description("식재료 이름"),
            fieldWithPath("data.recipeMaterialList[].materialOpecity").type(STRING).description("식재료 양"),
            fieldWithPath("data.recipeSteps[].recipeStepContent").type(STRING).description("레시피 단계 내용"),
            fieldWithPath("data.recipeSteps[].recipeStepImg").type(STRING).description("레시피 단계 이미지"),
            fieldWithPath("data.recipeId").type(STRING).description("레시피 고유 아이디")
    );

    public static final Snippet pickyRecipeListResponseField = responseFields(
            fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
            fieldWithPath("data[].recipeTitle").type(STRING).description("레시피 이름"),
            fieldWithPath("data[].recipeImgUrl").type(STRING).description("레시피 이미지 URL"),
            fieldWithPath("data[].recipeMaterialList[].materialName").type(STRING).description("식재료 이름"),
            fieldWithPath("data[].recipeMaterialList[].materialOpecity").type(STRING).description("식재료 양"),
            fieldWithPath("data[].recipeSteps[].recipeStepContent").type(STRING).description("레시피 단계 내용"),
            fieldWithPath("data[].recipeSteps[].recipeStepImg").type(STRING).description("레시피 단계 이미지"),
            fieldWithPath("data[].recipeId").type(STRING).description("레시피 고유 아이디")
    );
}
