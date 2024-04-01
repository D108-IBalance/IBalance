package com.ssafy.ibalance.picky;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.ChildDocument;
import com.ssafy.ibalance.child.util.AllergyTestUtil;
import com.ssafy.ibalance.child.util.ChildTestUtil;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.util.DietTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PickyApiTest extends ApiTest {

    @Autowired
    private DietTestUtil dietTestUtil;

    @Autowired
    private MemberTestUtil memberTestUtil;

    @Autowired
    private ChildTestUtil childTestUtil;

    @Autowired
    private AllergyTestUtil allergyTestUtil;

    @BeforeEach
    void settings() {
        allergyTestUtil.알러지정보_저장();
    }


//    @Test
//    void 편식정보_가져오기_성공_200() throws Exception {
//        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
//        String token = memberInfo.getFirst();
//        Integer childId = Integer.parseInt(memberInfo.getLast());
//
//        mockMvc.perform(
//                        get("/picky/{childId}", childId)
//                                .header(AUTH_HEADER, token)
//                                .param("limit", "WEEKLY")
//                )
//                .andExpect(status().isOk())
//                .andDo(this::print);
//
//    }

    @Test
    void 편식정보_가져오기_아이아이디_음수_400() {

    }

    @Test
    void 편식정보_가져오기_토큰없음_401() {

    }

    @Test
    void 편식정보_가져오기_권한없음_403() {

    }

    @Test
    void 편식정보_가져오기_아이없음_404() {

    }


    @Test
    void 편식레시피_가져오기_성공_200() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();
        Integer childId = Integer.parseInt(memberInfo.getLast());

        mockMvc.perform(
                        get("/picky/solution/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("material", "마늘")
                                .param("offset", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "아이가 잘 먹지 못하는 음식을 맛있게 만들어 주는 레시피 정보를 제공하는 API 입니다." +
                                "<br>정상적으로 매개변수를 넣었을 때, 200 OK 와 함께 원하는 갯수만큼 레시피 정보가 반환됩니다." +
                                "<br>material, offset 은 필수값입니다. 둘 중 하나를 넣지 않았거나, offset 이 1 이상의 정수가 아니라면" +
                                "<br>400 Bad Request 가 body 에 담겨 반환됩니다." +
                                "<br>lastId 는 선택값입니다. 넣지 않았을 경우, 가장 상위에 랭크된 편식 레시피부터 반환됩니다." +
                                "<br>로그인 토큰 값을 입력하지 않았을 경우, 401 Unauthorized 가 HTTP Status Code 에 담겨 반환됩니다." +
                                "<br>아이의 정보에 접근할 권한이 없을 경우, 403 Forbidden 이 body 에 담겨 반환됩니다." +
                                "<br>아이를 찾지 못하는 경우, 404 Not Found 가 body 에 담겨 반환됩니다." +
                                "<br>식재료를 잘못 입력했을 경우, 406 Not Acceptable 이 반환됩니다.", "편식레시피 찾기",
                        CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam,
                        PickyDocument.pickyRecipeListResponseField))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_가져오기_재료누락_400() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();
        Integer childId = Integer.parseInt(memberInfo.getLast());

        mockMvc.perform(
                        get("/picky/solution/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("material", "마늘")
                                .param("offset", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_가져오기_토큰없음_401() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        Integer childId = Integer.parseInt(memberInfo.getLast());

        mockMvc.perform(
                        get("/picky/solution/{childId}", childId)
                                .param("material", "마늘")
                                .param("offset", "5")
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_가져오기_권한없음_403() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();
        Integer childId = Integer.parseInt(memberInfo.getLast());

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/picky/solution/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                                .param("material", "마늘")
                                .param("offset", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_가져오기_아이없음_404() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();

        mockMvc.perform(
                        get("/picky/solution/{childId}", 999999)
                                .header(AUTH_HEADER, token)
                                .param("material", "마늘")
                                .param("offset", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_가져오기_편식데이터_이용불가_406() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();
        Integer childId = Integer.parseInt(memberInfo.getLast());

        mockMvc.perform(
                        get("/picky/solution/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("material", "aaaa")
                                .param("offset", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(406))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        PickyDocument.pickyRecipeRequestParam))
                .andDo(this::print);
    }

    @Test
    void 편식레시피_하나_가져오기_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        String material = "마늘";
        String recipeId = "66097184ca2d2f3820e1ecc9";

        mockMvc.perform(
                        get("/picky/detail/{material}/{recipeId}", material, recipeId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, "식재료와 레시피 아이디를 기준으로, 상세 레시피를 가져오는 API 입니다." +
                                "<br>정상적으로 상세 레시피를 받아왔다면, 200 OK 와 함께 레시피 내용이 반환됩니다." +
                                "<br>로그인 토큰이 없을 때, 401 Unauthorized 가 HTTP Status Code 로 반환됩니다." +
                                "<br>편식 레시피를 받아오고자 하는 정보에 이상이 있을 때, 406 Not Acceptable 이 body 에 반환됩니다.",
                        "편식레시피 하나조회", CommonDocument.AccessTokenHeader,
                        PickyDocument.onePickyRecipePathParam,
                        PickyDocument.pickyRecipeResponseField));

    }

    @Test
    void 편식레시피_하나_가져오기_토큰없음_401() throws Exception {

        String material = "마늘";
        String recipeId = "66097184ca2d2f3820e1ecc9";

        mockMvc.perform(
                        get("/picky/detail/{material}/{recipeId}", material, recipeId)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, PickyDocument.onePickyRecipePathParam));
    }

//    @Test
//    void 편식레시피_하나_가져오기_정보이상_406() throws Exception {
//        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
//
//        String material = "마늘";
//        String recipeId = "6asdfdddd";
//
//        mockMvc.perform(
//                        get("/picky/detail/{material}/{recipeId}", material, recipeId)
//                                .header(AUTH_HEADER, token)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(406))
//                .andDo(this::print)
//                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
//                        PickyDocument.onePickyRecipePathParam));
//    }


    List<String> 아이_편식정보_저장_프로세스(MockMvc mockMvc) throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.편식정보_저장(dietList, true);

        return List.of(token, Integer.toString(childId));
    }
}
