package com.ssafy.ibalance.picky;

import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.util.AllergyTestUtil;
import com.ssafy.ibalance.child.util.ChildTestUtil;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.util.DietTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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


    @Test
    void 편식정보_가져오기_성공_200() throws Exception {
        List<String> memberInfo = 아이_편식정보_저장_프로세스(mockMvc);
        String token = memberInfo.getFirst();
        Integer childId = Integer.parseInt(memberInfo.getLast());

        mockMvc.perform(
                        get("/picky/{childId}", childId)
                                .header(AUTH_HEADER, token)
                                .param("limit", "WEEKLY")
                )
                .andExpect(status().isOk())
                .andDo(this::print);

    }

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


    List<String> 아이_편식정보_저장_프로세스(MockMvc mockMvc) throws Exception {
        String token = memberTestUtil.회원가입_토큰반환(mockMvc);
        Integer childId = childTestUtil.아이_등록(token, mockMvc);

        List<Diet> dietList = dietTestUtil.식단정보_저장(childId);
        dietTestUtil.편식정보_저장(dietList, true);

        return List.of(token, Integer.toString(childId));
    }
}
