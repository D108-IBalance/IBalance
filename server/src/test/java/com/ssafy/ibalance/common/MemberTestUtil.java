package com.ssafy.ibalance.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.member.MemberSteps;
import com.ssafy.ibalance.member.dto.response.GoogleMemberInfoResponse;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
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
        return 회원가입(mockMvc, oneCode);
    }

    public String 회원가입_다른유저_토큰반환(MockMvc mockMvc) throws Exception {
        return 회원가입(mockMvc, otherCode);
    }

    private String 회원가입(MockMvc mockMvc, String code) throws Exception {
        memberRepository.save(Member.builder()
                .code(MemberTestUtil.oneCode)
                .provider(OAuthProvider.GOOGLE)
                .build());

        MvcResult mvcResult = mockMvc.perform(
                        post("/member/login/{provider}", "google")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSteps.로그인_생성(code)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(cookie().exists("refreshToken"))
                .andReturn();

        return getValueFromJSONBody(mvcResult, "$.data.accessToken", "");
    }

    public static GoogleMemberInfoResponse mockOAuthInfo(String code) {
        GoogleMemberInfoResponse response = new GoogleMemberInfoResponse();
        response.setSub(code);
        return response;
    }
}
