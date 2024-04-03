package com.ssafy.ibalance.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.member.MemberSteps;
import com.ssafy.ibalance.member.dto.response.GoogleMemberInfoResponse;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Component
public class MemberTestUtil extends TestBase {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberSteps memberSteps;

    public static String oneCode = "3387150673";
    public static String otherCode = "1234567812345";

    public static String googleRedirectUrl = "http://localhost:8080/member/login/google";
    public static String kakaoRedirectUrl = "http://localhost:8080/member/login/kakao";

    public String 회원가입_토큰반환(MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = 회원가입_후_로그인(mockMvc, oneCode);
        return getValueFromJSONBody(mvcResult, "$.data.accessToken", "");

    }

    public String 회원가입_다른유저_토큰반환(MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = 회원가입_후_로그인(mockMvc, otherCode);
        return getValueFromJSONBody(mvcResult, "$.data.accessToken", "");
    }

    public Cookie 회원가입_쿠키반환(MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = 회원가입_후_로그인(mockMvc, oneCode);
        return mvcResult.getResponse().getCookie("refreshToken");
    }

    private MvcResult 회원가입_후_로그인(MockMvc mockMvc, String code) throws Exception {
        memberRepository.save(Member.builder()
                .code(MemberTestUtil.oneCode)
                .provider(OAuthProvider.GOOGLE)
                .build());

        return mockMvc.perform(
                        post("/member/login/{provider}", "google")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSteps.로그인_생성(code)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(cookie().exists("refreshToken"))
                .andReturn();
    }

    public static GoogleMemberInfoResponse mockOAuthInfo(String code) {
        GoogleMemberInfoResponse response = new GoogleMemberInfoResponse();
        response.setSub(code);
        return response;
    }
}
