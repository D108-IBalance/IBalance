package com.ssafy.ibalance.diary;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.ChildDocument;
import com.ssafy.ibalance.child.util.AllergyTestUtil;
import com.ssafy.ibalance.child.util.ChildTestUtil;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.diary.dto.request.DiarySaveRequest;
import com.ssafy.ibalance.diet.dto.DietTotalInfoDto;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.diet.util.DietTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryApiTest extends ApiTest {

    @Autowired
    private MemberTestUtil memberTestUtil;

    @Autowired
    private ChildTestUtil childTestUtil;

    @Autowired
    private AllergyTestUtil allergyTestUtil;

    @Autowired
    private DietTestUtil dietTestUtil;

    @Autowired
    private DiarySteps diarySteps;

    @Autowired
    private DietRepository dietRepository;

    @BeforeEach
    void settings() {
        allergyTestUtil.알러지정보_저장();
    }

    @Test
    void 일기_캘린더_조회_성공_날짜있음_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, "일기장 페이지에서 식단이 존재하는 날짜를 조회하는 API 입니다." +
                                "<br><br><b>JWT 토큰과 자녀 아이디, 조회하고 싶은 연, 월</b>을 올바르게 입력하면, <b>200 OK</b>와 함께 식단이 존재하는 날짜와 모든 식단 리뷰 여부가 반환됩니다." +
                                "<br>- 자녀 아이디는 <b>1 이상의 정수</b>로 입력해야 합니다." +
                                "<br>- 0 이하의 정수를 입력하면 <b>400 Bad Request</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 1월 ~ 12월이 아닌 월을 입력하면 <b>400 Bad Request</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- <b>Header</b> 에 <b>JWT 토큰</b>이 올바르게 입력되지 않았을 때, <b>401 Unauthorized</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 자녀 정보에 접근 권한이 없을 때, <b>403 Forbidden</b> 이 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 아이디로 된 자녀를 찾을 수 없을 때, <b>404 Not Found</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다.",
                        "일기장_캘린더 조회", CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField,
                        DiaryDocument.getCalendarListResponseField
                ));
    }

    @Test
    void 일기_캘린더_조회_성공_날짜없음_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField,
                        DiaryDocument.getCalendarListResponseField
                ));
    }

    @Test
    void 일기_캘린더_조회_잘못된아이디_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = -1;

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField
                ));
    }

    @Test
    void 일기_캘린더_조회_잘못된월_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("year", "2024")
                                .param("month", "13")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField
                ));
    }

    @Test
    void 일기_캘린더_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField
                ));
    }

    @Test
    void 일기_캘린더_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField
                ));
    }

    @Test
    void 일기_캘린더_조회_없는아이_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = 999999;

        mockMvc.perform(
                        get("/diary/calendar/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("year", "2024")
                                .param("month", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, DiaryDocument.yearMonthQueryField
                ));
    }

    @Test
    void 일기_식단_조회_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("date", LocalDate.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, "일기장 페이지에서 선택한 날짜의 식단을 조회하는 API 입니다." +
                                "<br><br><b>JWT 토큰과 자녀 아이디, 조회하고 싶은 날짜(yyyy-MM-dd)</b>를 올바르게 입력하면, <b>200 OK</b>와 함께 식단 목록이 반환됩니다." +
                                "<br>- 자녀 아이디는 <b>1 이상의 정수</b>로 입력해야 합니다." +
                                "<br>- 0 이하의 정수를 입력하면 <b>400 Bad Request</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- (yyyy-MM-dd) 형식에 맞지 않는 날짜를 입력하면 <b>400 Bad Request</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- <b>Header</b> 에 <b>JWT 토큰</b>이 올바르게 입력되지 않았을 때, <b>401 Unauthorized</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 자녀 정보에 접근 권한이 없을 때, <b>403 Forbidden</b> 이 <b>body</b> 의 <b>status</b> 로 반환됩니다." +
                                "<br>- 해당 아이디로 된 자녀를 찾을 수 없을 때, <b>404 Not Found</b> 가 <b>body</b> 의 <b>status</b> 로 반환됩니다.",
                        "일기장_식단 조회", CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField,
                        DiaryDocument.getDietByDateResponseField
                ));
    }

    @Test
    void 일기_식단_조회_잘못된아이디_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = -1;

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("date", LocalDate.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField
                ));
    }

    @Test
    void 일기_식단_조회_잘못된날짜_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("date", "2024/03/24")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField
                ));
    }

    @Test
    void 일기_식단_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .param("date", LocalDate.now().toString())
                )
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField
                ));
    }

    @Test
    void 일기_식단_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                                .param("date", LocalDate.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField
                ));
    }

    @Test
    void 일기_식단_조회_없는아이_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = 999999;

        mockMvc.perform(
                        get("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("date", LocalDate.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        ChildDocument.childIdPathField, CommonDocument.DateQueryField
                ));
    }

    @Test
    void 식단_일기_작성전_조회_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);
        dietTestUtil.편식정보_저장(dietList, false);

        mockMvc.perform(
                        get("/diary/detail/{dietId}", dietList.getFirst().getId())
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "식단 일기를 작성하기 위해 제공하는 API 입니다." +
                                "<br>일기를 작성하고자 하는 정상적인 식단 아이디를 path 에 넣어 반환하면, 20O OK 와 함께 해당 식단의 " +
                                "<br>상세 정보가 반환됩니다." +
                                "<br>0 이하의 식단 ID 를 입력 시, 400 Bad Request 가 body 에 담겨 반환됩니다." +
                                "<br>토큰을 입력하지 않았을 경우, 401 Unauthorized 가 body 에 담겨 반환됩니다." +
                                "<br>해당 식단을 조회할 수 있는 권한이 없는 경우, 403 Forbidden 이 반환됩니다." +
                                "<br>해당 식단을 찾을 수 없는 경우, 404 Not Found 가 반환됩니다.", "일기용 식단상세조회",
                        CommonDocument.AccessTokenHeader, DiaryDocument.dietIdPathField,
                        DiaryDocument.diaryInfoResponseField));
    }

    @Test
    void 식단_일기_작성전_조회_식단아이디음수_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);
        dietTestUtil.편식정보_저장(dietList, false);

        mockMvc.perform(
                        get("/diary/detail/{dietId}", -1)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, DiaryDocument.dietIdPathField));
    }

    @Test
    void 식단_일기_작성전_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);
        dietTestUtil.편식정보_저장(dietList, false);

        mockMvc.perform(
                        get("/diary/detail/{dietId}", dietList.getFirst().getId())
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, DiaryDocument.dietIdPathField));
    }

    @Test
    void 식단_일기_작성전_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);
        dietTestUtil.편식정보_저장(dietList, false);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/diary/detail/{dietId}", dietList.getFirst().getId())
                                .header(AUTH_HEADER, otherToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, DiaryDocument.dietIdPathField));
    }

    @Test
    void 식단_일기_작성전_조회_식단없음_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.식단_메뉴_저장(dietList);
        dietTestUtil.편식정보_저장(dietList, false);

        mockMvc.perform(
                        get("/diary/detail/{dietId}", 9999999)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH,
                        CommonDocument.AccessTokenHeader, DiaryDocument.dietIdPathField));
    }

    @Test
    void 식단_일기_저장_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId, "LUNCH");

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "식단 일기를 저장하는 API 입니다." +
                                "<br>정상적으로 식단을 저장했을 경우, 200 OK 와 함께 저장 결과가 사용자에게 반환됩니다." +
                                "<br>식단 일기 저장 시, 아래의 규칙을 만족하지 않는다면 body 에 400 Bad Request 가 반환됩니다." +
                                "<br>1. 식단 아이디는 반드시 입력해야 하며, 1 이상의 정수를 입력해야 합니다." +
                                "<br>2. 일기 내용 또한 반드시 입력해야 하며, 1자 이상의 내용을 입력해야 합니다." +
                                "<br>3. 메뉴 4개에 대한 별점을 반드시 입력해야 하며, 1점 이상 5점 이하로 입력해야 합니다." +
                                "<br>4. 받은 식재료 아이디는 1 이상의 정수로 입력해야 하며, 편식 정보가 없는 경우에도 빈 배열을 넣어 주어야 합니다." +
                                "<br>5. 아침/점심/저녁/해당없음 은 BREAKFAST/LUNCH/DINNER/NONE 으로, 대문자로 적어 주셔야 하며, 입력하지 않았을 경우 자동으로 NONE 저장됩니다." +
                                "<br><br>토큰을 입력하지 않았을 경우, 401 Unauthorized 가 HTTP Status Code 에 반환됩니다." +
                                "<br>식단이 저장된 child 가 입력한 child id 와 다르거나, 권한이 없는 사용자가 해당 식단 아이디로 일기를 저장하려고 하는 경우, " +
                                "<br>403 Forbidden 이 body 에 담겨 반환됩니다." +
                                "<br>입력한 dietId 로 식단을 조회할 수 없는 경우, 404 Not Found 가 body 에 담겨 반환됩니다." +
                                "<br>입력해 주는 menu id 정보가 식단에 포함되어 있지 않았을 경우, 409 Conflict 가 body 에 담겨 반환됩니다.",
                        "식단일기작성", CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField, DiaryDocument.diarySaveResponseField))
                .andDo(this::print);

        DietTotalInfoDto dietTotalInfo = dietRepository.getDietTotalInfo(targetDietId);

        assertThat(dietTotalInfo.getDiet().getDiary()).isEqualTo(DiarySteps.content);
        assertThat(dietTotalInfo.getDietMaterialList().getFirst().isPicky()).isTrue();
    }

    @Test
    void 식단_일기_저장_성공_식사시간누락_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId);

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField, DiaryDocument.diarySaveResponseField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_저장_유효성검증_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        List<DietMenu> dietMenuList = dietTestUtil.식단_메뉴_저장(dietList);
        List<DietMaterial> materials = dietTestUtil.편식정보_저장(dietList, false);

        DiarySaveRequest requestBody =
                diarySteps.식단_일기_일기누락(targetDietId, dietMenuList, materials, List.of(1L, 4L));

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_저장_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId);

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_저장_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId);

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, otherToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_저장_식단없음_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId);

        requestBody.setDietId(9999999L);

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_저장_메뉴정보충돌_409() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId);

        requestBody.getMenuRate().getFirst().setMenuId("123456677");

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(409))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, ChildDocument.childIdPathField,
                        DiaryDocument.saveDiaryRequestField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        Long dietId = 식단_일기_저장(token, childId, "BREAKFAST");

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, dietId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document(DEFAULT_RESTDOC_PATH, "작성된 식단의 일기를 조회하는 API 입니다." +
                                "<br>정상적으로 일기를 조회했을 경우, 200 OK 와 함께 식단 일기 정보가 반환됩니다." +
                                "<br>childId 나 dietId 를 음수로 입력했을 경우, 400 Bad Request 가 body 에 담겨 반환됩니다." +
                                "<br>로그인 토큰을 헤더에 담지 않았을 경우, 401 Unauthorized 가 HTTP Status Code 로 반환됩니다." +
                                "<br>childId 의 아이에 접근할 권한이 없거나, diet 에 접근할 권한이 없을 경우, 403 Forbidden 이 " +
                                "<br>body 에 담겨 반환됩니다." +
                                "<br>dietId 에 접근할 권한이 없을 경우, 404 Not Found 가 body 에 담겨 반환됩니다." +
                                "<br>아직 일기를 작성하지 못했음에도 불구하고 식단 일기를 확인하고자 하는 경우, 409 Conflict 가 반횐됩니다.",
                        "식단일기조회", CommonDocument.AccessTokenHeader, DiaryDocument.childAndDietIdPathField,
                        DiaryDocument.writtenDiaryResponseField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_아이디음수_400() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        식단_일기_저장(token, childId, "BREAKFAST");

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, -1)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        DiaryDocument.childAndDietIdPathField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_토큰없음_401() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        Long dietId = 식단_일기_저장(token, childId, "BREAKFAST");

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, dietId)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH, DiaryDocument.childAndDietIdPathField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_권한없음_403() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        Long dietId = 식단_일기_저장(token, childId, "BREAKFAST");

        String otherToken = memberTestUtil.회원가입_다른유저_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, dietId)
                                .header(AUTH_HEADER, otherToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        DiaryDocument.childAndDietIdPathField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_식단없음_404() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        식단_일기_저장(token, childId, "BREAKFAST");

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, 9999999)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader,
                        DiaryDocument.childAndDietIdPathField))
                .andDo(this::print);
    }

    @Test
    void 식단_일기_조회_일기없음_409() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        식단_전반_정보_저장(dietList, targetDietId);

        mockMvc.perform(
                        get("/diary/{childId}/detail/{dietId}", childId, targetDietId)
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(409))
                .andDo(document(DEFAULT_RESTDOC_PATH, CommonDocument.AccessTokenHeader, DiaryDocument.childAndDietIdPathField))
                .andDo(this::print);
    }

    Long 식단_일기_저장(String token, Integer childId, String mealTime) throws Exception {
        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        Long targetDietId = dietList.getFirst().getId();
        DiarySaveRequest requestBody = 식단_전반_정보_저장(dietList, targetDietId, mealTime);

        mockMvc.perform(
                        post("/diary/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));

        return targetDietId;
    }

    DiarySaveRequest 식단_전반_정보_저장(List<Diet> dietList, Long targetId) {
        return 식단_전반_정보_저장(dietList, targetId, null);
    }

    DiarySaveRequest 식단_전반_정보_저장(List<Diet> dietList, Long targetId, String mealTime) {
        List<DietMenu> dietMenuList = dietTestUtil.식단_메뉴_저장(dietList);
        List<DietMaterial> materials = dietTestUtil.편식정보_저장(dietList, false);

        return diarySteps.식단_일기_저장(targetId, dietMenuList, materials, List.of(1L, 4L), mealTime);
    }
}
