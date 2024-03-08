package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.response.KakaoInfoResponseDto;

public interface OauthService {
    public KakaoInfoResponseDto getKakaoInfo(String code);
    public String getKakaoToken(String code);
}
