package com.ssafy.ibalance.common.dto.response;

import lombok.Getter;

@Getter
public class StringWrapper {

    private String value;

    public static StringWrapper wrap(String value) {
        return new StringWrapper(value);
    }

    private StringWrapper(String value) {
        this.value = value;
    }
}
