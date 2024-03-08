package com.ssafy.ibalance.member.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class KakaoInfoResponseDto {
    private Long id;
    private Timestamp connecetedAt;
}
