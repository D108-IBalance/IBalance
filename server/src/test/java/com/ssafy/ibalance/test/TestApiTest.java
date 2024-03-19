package com.ssafy.ibalance.test;

import com.jayway.jsonpath.JsonPath;
import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.common.CommonDocument;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.test.dto.request.TestSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestApiTest extends ApiTest {

    @Autowired
    private MemberTestUtil memberTestUtil;

    @Test
    void helloTest() throws Exception {
        String name = "dongwoo";
        mockMvc.perform(
                get("/test/hello/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.value").value("Hello " + name))
                .andDo(
                        document(DEFAULT_RESTDOC_PATH, "Swagger 기본 기능을 검증합니다.",
                                "스웨거검증")
                );
    }

    @Test
    void JPA_Test() throws Exception {
        String daegu = "Daegu";

        test_entity_save("dongwoo", "Daegu Dalseo Bolli");
        test_entity_save("whalesbob", "Daegu Bukgu Sangyeok");

        mockMvc.perform(
                        get("/test/jpa/{name}", daegu)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andDo(handler -> System.out.println(handler.getResponse().getContentAsString()));
    }

    @Test
    void QueryDSL_Test() throws Exception{
        String dongwooAddr = "Daegu Dalseo Bolli";
        test_entity_save("dongwoo", dongwooAddr);
        test_entity_save("whalesbob", "Daegu Bukgu Sangyeok");

        String targetUrl = "/test/querydsl/" + "dongwoo";

        MvcResult mvcResult = mockMvc.perform(
                        get(targetUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String name = JsonPath.parse(result).read("$.data.[0].address");
        assertThat(name).contains(dongwooAddr);
    }

    @Test
    void exception_test() throws Exception{
        mockMvc
                .perform(
                get("/test/exceptional")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andDo(handler -> {
                    System.out.println(handler.getResponse().getHeader("Content-Type"));
                    System.out.println(handler.getResponse().getContentAsString());
                });
    }

    @Test
    void login_test_200() throws Exception{

        String token = memberTestUtil.회원가입_토큰반환(mockMvc);

        mockMvc.perform(
                get("/test/login")
                        .header(AUTH_HEADER, token)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(
                        document(DEFAULT_RESTDOC_PATH, "Swagger 기본 기능을 검증합니다.",
                                "로그인검증", CommonDocument.AccessTokenHeader)
                );;
    }

    @Test
    void login_test_401() throws Exception{
        mockMvc.perform(
                        get("/test/login")
                )
                .andExpect(status().is(401))
                .andDo(handler -> System.out.println(handler.getResponse().getContentAsString()));
    }

    void test_entity_save(String name, String address) throws Exception {
        TestSaveRequest request = TestSaveRequest.builder()
                .name(name)
                .address(address)
                .build();

        mockMvc.perform(
                post("/test/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk());
    }
}
