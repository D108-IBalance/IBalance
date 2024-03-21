package com.ssafy.ibalance.child;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.util.AllergyTestUtil;
import com.ssafy.ibalance.child.util.ChildTestUtil;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
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

    @BeforeEach
    void settings(){
        allergyTestUtil.알러지정보_저장();
    }


    @Test
    void 아이_정보_등록_성공_200() throws Exception {

        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        mockMvc.perform(
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
                );

        Mockito.verify(redisUtil, times(1)).setChildAllergy(anyInt(), any());
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
    void 아이_정보_등록_토큰없음_401() throws Exception{
        mockMvc.perform(
                        post("/child")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(childSteps.아이정보_생성()))
                )
                .andExpect(status().isOk())
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
    void 아이_정보_가져오기_1명_200() throws Exception{
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(document(DEFAULT_RESTDOC_PATH));
    }
}