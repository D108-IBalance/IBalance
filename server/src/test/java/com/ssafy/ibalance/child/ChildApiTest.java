package com.ssafy.ibalance.child;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.util.AllergyTestUtil;
import com.ssafy.ibalance.child.util.ChildTestUtil;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.util.DietTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChildApiTest extends ApiTest {

    @Autowired
    private MemberTestUtil memberTestUtil;

    @Autowired
    private AllergyTestUtil allergyTestUtil;

    @Autowired
    private ChildTestUtil childTestUtil;

    @Autowired
    private ChildSteps childSteps;

    @Autowired
    private ChildAllergyRepository childAllergyRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private DietTestUtil dietTestUtil;

    @BeforeEach
    void settings() {
        allergyTestUtil.알러지정보_저장();
    }

    @Test
    void 아이_정보_등록_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        MvcResult mvcResult = mockMvc.perform(
                        post("/child")
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(childSteps.아이정보_생성()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(
                        document(DEFAULT_RESTDOC_PATH, "아이 정보를 등록하는 API 입니다. " +
                                        "<br>모든 값을 조건에 맞게 입력했을 경우, 200 OK 와 함께 아이 정보가 반환됩니다." +
                                        "<br>아이디 토큰을 헤더에 담아 주는 것은 필수값입니다. 존재하지 않거나 잘못된 토큰 입력 시 401 Unauthorized 가 body 내 반환됩니다" +
                                        "<br>Request 에서의 검증내용은 아래와 같습니다. 아래의 조건을 지키지 못하면 400 Bad Request 가 반환됩니다." +
                                        "<br>1. 이름은 1자 이상 20자 이하, 반드시 입력요망" +
                                        "<br>2. 생년월일은 yyyy-mm-dd 형식" +
                                        "<br>3. gender 는 MALE/FEMALE" +
                                        "<br>4. height, weight 는 반드시 소수점 1자리까지만! 소수점이 없는 것은 상관없습니다." +
                                        "<br>5.1 haveAllergies 는 정수 배열이어야 하며, 1부터 18자리 숫자 이하로 입력해야 합니다. " +
                                        "<br>5.2 haveAllergies 배열이 비어 있어도 상관없습니다. 대신 빈 배열을 던져 주세요.",
                                "아이정보등록",
                                CommonDocument.AccessTokenHeader,
                                ChildDocument.registerChildRequestField,
                                ChildDocument.registerChildResponseField)
                )
                .andReturn();

        Integer childId = getValueFromJSONBody(mvcResult, "$.data.id", 0);

        assertThat(childAllergyRepository.findByChild_id(childId).size()).isEqualTo(2);
    }

    @Test
    void 아이_정보_등록_정보이상_400() throws Exception {
        allergyTestUtil.알러지정보_저장();
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        mockMvc.perform(
                        post("/child")
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(childSteps.아이정보_잘못_생성()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(this::print)
                .andDo(
                        document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.registerChildRequestField)
                );
    }

    @Test
    void 아이_정보_등록_토큰없음_401() throws Exception {
        mockMvc.perform(
                        post("/child")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(childSteps.아이정보_생성()))
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH));
    }

    @Test
    void 아이_정보_가져오기_비어있음_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/child")
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader));
    }

    @Test
    void 아이_정보_가져오기_1명_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                        get("/child")
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data.[0].childId").value(childId))
                .andDo(document(DEFAULT_RESTDOC_PATH, "아이 정보를 가져오는 API 입니다. " +
                                "<br>Header 에 유효한 JWT 토큰을 넣어 주면, 200 OK 와 함께 아이들의 정보 리스트가 반환됩니다." +
                                "<br>유효하지 않은 JWT 토큰을 헤더에 입력하거나, 입력하지 않으면 401 Unauthorized 가 Body 내 status 에 반환됩니다.",
                        "아이정보조회",
                        CommonDocument.AccessTokenHeader,
                        ChildDocument.findChildResponseField));
    }

    @Test
    void 아이_정보_가져오기_토큰없음_401() throws Exception {
        mockMvc.perform(
                        get("/child")
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH));
    }

    @Test
    void 아이_정보_삭제_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        MvcResult mvcResult = mockMvc.perform(
                        delete("/child/{childId}", childId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "아이 정보를 삭제하는 API 입니다. " +
                                "<br>성공적으로 아이 정보가 삭제되면 200 OK 와 함께 삭제된 아이의 대략적인 정보가 반환됩니다." +
                                "<br>자녀의 Primary Key 는 1 이상의 정수로 입력해야 합니다." +
                                "<br>0 이하의 정수를 입력하면 400 Bad Request 가 body 내에 들어가 반환됩니다." +
                                "<br>JWT 토큰이 헤더에 올바르게 입력되지 않았을 때, 401 Unauthorized 가 반환됩니다." +
                                "<br>해당 자녀 정보에 접근할 권한이 없을 때, 403 Forbidden 이 반환됩니다." +
                                "<br>해당 아이디로 된 자녀를 찾을 수 없을 때, 404 Not Found 가 반환됩니다.",
                        "자녀정보삭제", CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField,
                        ChildDocument.deletedChildResponseField
                ))
                .andReturn();

        Integer deletedChildId = getValueFromJSONBody(mvcResult, "$.data.id", 0);

        assertThat(childRepository.findById(deletedChildId)).isEmpty();
        assertThat(childAllergyRepository.findByChild_id(deletedChildId).size()).isEqualTo(0);
    }

    @Test
    void 아이_정보_삭제_잘못된아이디_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = -1;

        mockMvc.perform(
                        delete("/child/{childId}", childId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 아이_정보_삭제_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                        delete("/child/{childId}", childId)
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, ChildDocument.childIdPathField));
    }

    @Test
    void 아이_정보_삭제_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        delete("/child/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 아이_정보_삭제_없는아이_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = 9999999;

        mockMvc.perform(
                        delete("/child/{childId}", childId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 메인_정보_조회_식단있음_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        mockMvc.perform(
                        get("/child/main/{childId}", childId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.childDetailResponse.childId").value(childId))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, "메인 페이지에서 아이의 정보와 오늘의 식단을 조회하는 API 입니다." +
                                "<br>JWT 토큰과 자녀 아이디를 올바르게 입력하면, 200 OK 와 함께 아이의 정보와 오늘의 식단이 반환됩니다." +
                                "<br>- 자녀 아이디는 1 이상의 정수로 입력해야 합니다." +
                                "<br>- 0 이하의 정수를 입력하면 400 Bad Request 가 body 의 status 로 반환됩니다." +
                                "<br>- Header 에 JWT 토큰이 올바르게 입력되지 않았을 때, 401 Unauthorized 가 body 의 status 로 반환됩니다." +
                                "<br>- 해당 자녀 정보에 접근 권한이 없을 때, 403 Forbidden 이 body 의 status 로 반환됩니다." +
                                "<br>- 해당 아이디로 된 자녀를 찾을 수 없을 때, 404 Not Found 가 body 의 status 로 반환됩니다.",
                        "메인_자녀 정보, 오늘의 식단 조회", CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField,
                        ChildDocument.getMainResponseField
                ));
    }

    @Test
    void 메인_정보_조회_식단없음_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                    get("/child/main/{childId}", childId)
                        .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.childDetailResponse.childId").value(childId))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField,
                        ChildDocument.getMainResponseField
                        ));
    }

    @Test
    void 메인_정보_조회_잘못된아이디_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = -1;

        mockMvc.perform(
                    get("/child/main/{childId}", childId)
                        .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 메인_정보_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                    get("/child/main/{childId}", childId)
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, ChildDocument.childIdPathField));
    }

    @Test
    void 메인_정보_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/child/main/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 메인_정보_조회_없는아이_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = 9999999;

        mockMvc.perform(
                get("/child/main/{childId}", childId)
                        .header(AUTH_HEADER, token)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField));
    }

    @Test
    void 메인_성장_조회_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                get("/child/growth/{childId}", childId)
                        .header(AUTH_HEADER, token)
                        .param("page", "0")
                        .param("size", "4")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "메인 페이지에서 자녀의 성장 데이터와 평균 데이터를 조회하는 API 입니다." +
                                "<br><b>JWT 토큰과 자녀 아이디, page와 size</b>를 올바르게 입력하면, <b>200 OK</b> 와 함께 성장 데이터와 평균 데이터가 반환됩니다." +
                                "<br>- 자녀 아이디는 <b>1 이상의 정수</b>로 입력해야 합니다." +
                                "<br>- 0 이하의 정수를 입력하면 <b>400 Bad Request</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- <b>Header</b> 에 <b>JWT 토큰</b>이 올바르게 입력되지 않았을 때, <b>401 Unauthorized</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 자녀 정보에 접근 권한이 없을 때, <b>403 Forbidden</b> 이 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 아이디로 된 자녀를 찾을 수 없을 때, <b>404 Not Found</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다.",
                        "메인_자녀 성장 데이터, 평균 데이터 조회", CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, ChildDocument.pageableQueryField,
                        ChildDocument.getGrowthListResponseField
                        ));
    }

    @Test
    void 메인_성장_조회_잘못된아이디_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = -1;

        mockMvc.perform(
                get("/child/growth/{childId}", childId)
                        .header(AUTH_HEADER, token)
                        .param("page", "0")
                        .param("size", "4")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, ChildDocument.pageableQueryField
                ));
    }

    @Test
    void 메인_성장_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                get("/child/growth/{childId}", childId)
                        .param("page", "0")
                        .param("size", "4")
        )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        ChildDocument.childIdPathField, ChildDocument.pageableQueryField
                ));
    }

    @Test
    void 메인_성장_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                get("/child/growth/{childId}", childId)
                        .header(AUTH_HEADER, otherToken)
                        .param("page", "0")
                        .param("size", "4")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, ChildDocument.pageableQueryField
                ));
    }

    @Test
    void 메인_성장_조회_없는아이_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = 9999999;

        mockMvc.perform(
                get("/child/growth/{childId}", childId)
                        .header(AUTH_HEADER, token)
                        .param("page", "0")
                        .param("size", "4")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, ChildDocument.pageableQueryField
                ));
    }
}
