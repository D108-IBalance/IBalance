package com.ssafy.ibalance.common.dto.response;

import lombok.Builder;

@Builder
public record CommonWrapperResponse(

        Integer status,

        Object data
) {
}
