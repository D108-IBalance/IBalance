package com.ssafy.ibalance.member;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MemberApiTest extends ApiTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSteps memberSteps;

    @Autowired
    private MemberTestUtil memberTestUtil;


    @Test
    void 로그인_성공_200() throws Exception {
        memberRepository.save(Member.builder()
                .code(MemberTestUtil.oneCode)
                .provider(OAuthProvider.GOOGLE)
                .build());

        mockMvc.perform(
                        post("/member/login/{provider}", "google")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSteps.로그인_생성(MemberTestUtil.oneCode)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(cookie().exists("refreshToken"))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, "로그인을 처리하는 API 입니다. " +
                                "<br>소셜 로그인에서 받아온 정상적인 코드와, 타겟으로 하는 provider, 그리고 현재 처리하고 있는 redirect url 을 입력하면" +
                                "<br>200 OK 가 body 내 status 에 반환되며, data 에 JWT Access Token 관련 정보들이 반환됩니다." +
                                "<br>추가로, refresh token 이 cookie 에 반환됩니다." +
                                "<br>유효하지 않은 code 나 redirectUrl 을 입력 시, 403 Forbidden 이 반환됩니다." +
                                "<br>올바른 code 나 redirectUrl 을 입력했지만, 유저의 정보를 불러오는 데 실패했다면 404 Not Found 가 반환됩니다." +
                                "<br>google, naver, kakao 이외 다른 provider 를 입력하면 406 Not Acceptible 이 반환됩니다.", "소셜로그인 중간",
                        MemberDocument.providerPathField,
                        MemberDocument.loginRequestField,
                        MemberDocument.loginResultResponseField))
                .andReturn();
    }

    @Test
    void 로그인_유효하지않은_코드_403() throws Exception {
        mockMvc.perform(
                        post("/member/login/{provider}", "kakao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSteps.로그인_생성("1234asdf")))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, MemberDocument.providerPathField,
                        MemberDocument.loginRequestField));
    }

    @Test
    void 로그인_OAuth_유효하지않음_406() throws Exception {
        mockMvc.perform(
                        post("/member/login/{provider}", "SSAFY")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSteps.로그인_생성(MemberTestUtil.oneCode)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(406))
                .andDo(this::print)
                .andDo(document(DEFAULT_RESTDOC_PATH, MemberDocument.providerPathField,
                        MemberDocument.loginRequestField));
    }

    @Test
    void 로그아웃_성공_200() throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        mockMvc.perform(
                        get("/member/logout")
                                .header(AUTH_HEADER, token)
                )
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("refreshToken", 0))
                .andDo(document(DEFAULT_RESTDOC_PATH, "로그아웃을 처리하는 API 입니다." +
                                "<br>API 호출 시 200 OK 와 함께 현재 가지고 있는 refreshToken 를 삭제합니다." +
                                "<br>헤더가 없어도 200 처리되며, Negative Code 가 발생되지 않습니다.", "로그아웃",
                        CommonDocument.AccessTokenHeader));
    }
}
