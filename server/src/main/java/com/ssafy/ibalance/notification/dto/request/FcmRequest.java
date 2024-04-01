package com.ssafy.ibalance.notification.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FcmRequest {

    private String token;
}
