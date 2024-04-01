package com.ssafy.ibalance.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class NaverMemberInfoResponse implements OAuthMemberInfo {

    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private NaverDetailResponse response;

    @Override
    public String code() {
        return response.id();
    }
}
