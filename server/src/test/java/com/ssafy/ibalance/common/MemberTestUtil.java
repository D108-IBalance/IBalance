package com.ssafy.ibalance.common;

import com.ssafy.ibalance.member.dto.response.GoogleMemberInfoResponse;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Component
public class MemberTestUtil extends TestBase {

    @Autowired
    private MemberRepository memberRepository;

    public static String oneCode = "3387150673";
    public static String otherCode = "1234567812345";

    @Value("${google.redirect-uri}")
    public static String redirectUri;

    public String 회원가입_토큰반환(MockMvc mockMvc) throws Exception {
        return 회원가입(mockMvc, oneCode);
    }

    public String 회원가입_다른유저_토큰반환(MockMvc mockMvc) throws Exception{
        return 회원가입(mockMvc, otherCode);
    }

    private String 회원가입(MockMvc mockMvc, String code) throws Exception{
        memberRepository.save(Member.builder()
                .code(code)
                .provider(OAuthProvider.GOOGLE)
                .build());

        MvcResult mvcResult = mockMvc.perform(
                        get("/member/login/{provider}?code={code}", "google", code)
                )
                .andExpect(status().isOk())
                .andReturn();

        return getValueFromJSONBody(mvcResult, "$.data.accessToken", "");
    }

    public static GoogleMemberInfoResponse mockOAuthInfo(String code){
        GoogleMemberInfoResponse response = new GoogleMemberInfoResponse();
        response.setSub(code);
        return response;
    }
}
