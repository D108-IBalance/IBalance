package com.ssafy.ibalance.diary;

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

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
}
