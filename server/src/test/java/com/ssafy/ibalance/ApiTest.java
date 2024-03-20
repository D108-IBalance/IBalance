package com.ssafy.ibalance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.common.util.RedisUtil;
import com.ssafy.ibalance.member.util.GoogleOAuth2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @MockBean
    private GoogleOAuth2Utils googleOAuth2Utils;

    @MockBean
    protected RedisUtil redisUtil;

    public static final String AUTH_HEADER = "Authorization";

    protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}";

    @BeforeEach
    void setUp(){
        if(databaseCleanup.tableNames == null || databaseCleanup.tableNames.isEmpty()){
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.truncateAllTables();

        Mockito.when(googleOAuth2Utils.getUserInfo(MemberTestUtil.code, MemberTestUtil.redirectUri))
                .thenReturn(MemberTestUtil.mockOAuthInfo());

        Mockito.doNothing().when(redisUtil).setChildAllergy(Mockito.anyInt(), Mockito.any());
    }

    protected void print(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(result.getResponse().getContentAsString());
    }
}
