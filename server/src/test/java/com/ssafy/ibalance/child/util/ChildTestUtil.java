package com.ssafy.ibalance.child.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.child.ChildSteps;
import com.ssafy.ibalance.common.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class ChildTestUtil extends TestUtil {

    @Autowired
    private ChildSteps childSteps;

    @Autowired
    protected ObjectMapper objectMapper;

    public Integer 아이_등록(String token, MockMvc mockMvc) throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        post("/child")
                                .header(ApiTest.AUTH_HEADER, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(childSteps.아이정보_생성()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andReturn();

        return getValueFromJSONBody(mvcResult, "$.data.id", 0);
    }
}
