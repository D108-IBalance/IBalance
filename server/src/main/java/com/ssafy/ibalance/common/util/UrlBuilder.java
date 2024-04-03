package com.ssafy.ibalance.common.util;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class UrlBuilder {

    private static final StringBuilder builder = new StringBuilder();

    public String getUrl(String targetUri, Map<String, Serializable> parameterMap, boolean hasQuotationMark) {
        builder.delete(0, builder.length());
        builder.append(targetUri).append("?");

        parameterMap.forEach((key, value) -> {
            String parameter;
            if(isNotString(value) || !hasQuotationMark) {
                parameter = String.format("%s=%s&", key, value);
            } else {
                parameter = String.format("%s=\"%s\"&", key, value);
            }
            builder.append(parameter);
        });

        return builder.substring(0, builder.length() - 1);
    }

    private boolean isNotString(Serializable value) {
        if(value instanceof Number) {
            return true;
        }

        String convert = (String) value;
        return convert.isEmpty() || convert.isBlank();
    }
}
