package com.ssafy.ibalance.test;

import com.jayway.jsonpath.JsonPath;
import com.ssafy.ibalance.ApiTest;
import com.ssafy.ibalance.test.dto.request.TestSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestApiTest extends ApiTest {

    @Test
    void helloTest() throws Exception {
        String name = "dongwoo";
        String targetUrl = "/test/hello/" + name;
        mockMvc.perform(
                get(targetUrl)
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("hello " + name));
    }

    @Test
    void JPA_Test() throws Exception {
        String daegu = "Daegu";

        test_entity_save("dongwoo", "Daegu Dalseo Bolli");
        test_entity_save("whalesbob", "Daegu Bukgu Sangyeok");

        mockMvc.perform(
                        get("/test/jpa/" + daegu)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
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
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String name = JsonPath.parse(result).read("$.[0].address");
        assertThat(name).contains(dongwooAddr);
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
