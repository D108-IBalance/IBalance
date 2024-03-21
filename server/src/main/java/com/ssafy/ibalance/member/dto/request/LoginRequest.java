package com.ssafy.ibalance.member.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest {

    private String code;
    private String url;
}
