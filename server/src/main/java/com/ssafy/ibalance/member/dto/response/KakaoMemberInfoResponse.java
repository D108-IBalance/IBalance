package com.ssafy.ibalance.member.dto.response;

import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class KakaoMemberInfoResponse implements OAuthMemberInfo {
    private Long id;
    private Timestamp connecetedAt;

    @Override
    public String code() {
        return id.toString();
    }
}
