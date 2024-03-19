package com.ssafy.ibalance.common;

import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class TestUtil {

    protected String getValueFromJSONBody(MvcResult result, String path) throws UnsupportedEncodingException {
        String resultString = result.getResponse().getContentAsString();
        return JsonPath.parse(resultString).read(path);
    }
}
