package com.ssafy.ibalance.diary.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuRateRequest {
    private String menuId;
    private Double rate;
}
