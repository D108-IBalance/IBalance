package com.ssafy.ibalance.test.dto.request;

import lombok.Builder;

@Builder
public record TestSaveRequest(

        String name,

        String address
) {
}
