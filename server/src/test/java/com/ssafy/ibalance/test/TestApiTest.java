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

        MvcResult mvcResult = mockMvc.perform(
                        get("/test/querydsl/{name}", "dongwoo")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andDo(handler ->
                        System.out.println(handler.getResponse().getContentAsString()))
                .andDo(
                        document(DEFAULT_RESTDOC_PATH, "여기에 설명을 적습니다." +
                                "<br>br 태그를 통해 한 칸 띄워적을 수 있습니다." +
                                "<br>지금 내용처럼 섦명을 쭉쭉 이어나가면 됩니다." +
                                "<br>아래에는, Document 라는 클래스를 새로 만들고, static 으로 만들어 주면 됩니다. " +
                                "<br>pathField 에 들어가는 required() 는 필요할 때만 넣어 주고, required 가 아닐 경우" +
                                "<br> 뒤에 .isOptional() 과 같이 써서 반드시 받는 것은 아니라고 표기해 줄 수 있습니다." +
                                "<br>'[]' 는 배열을 표시할 때 사용할 수 있습니다. 배열이 아니라면 그냥 'data.abc' 와 같이" +
                                "<br>기술해 주면 됩니다." +
                                "<br>Header, Query String, Path Parameter, Response 다 따로 나눠 적어줍니다.",
                                "Swagger 설명",
                                TestDocument.testPathField, TestDocument.queryDslResponseField)
                )
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
                );
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
