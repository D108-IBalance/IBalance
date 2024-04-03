package com.ssafy.ibalance.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmResponse {

    private Integer memberId;
    private String token;
}
