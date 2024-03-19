package com.ssafy.ibalance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.common.MemberTestUtil;
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

    protected final String AUTH_HEADER = "Authorization";

    protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}";

    @BeforeEach
    void setUp(){
        if(databaseCleanup.tableNames == null || databaseCleanup.tableNames.isEmpty()){
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.truncateAllTables();

        Mockito.when(googleOAuth2Utils.getUserInfo(MemberTestUtil.code)).thenReturn(MemberTestUtil.mockOAuthInfo());
    }
}
