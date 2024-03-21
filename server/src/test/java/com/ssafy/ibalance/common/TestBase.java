package com.ssafy.ibalance.common;

import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class TestBase {

    protected <T> T getValueFromJSONBody(MvcResult result, String path, T targetClassObject) throws UnsupportedEncodingException {
        String resultString = result.getResponse().getContentAsString();
        return JsonPath.parse(resultString).read(path);
    }
}
