package com.ssafy.ibalance.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GoogleMemberInfoResponse implements OAuthMemberInfo {

    private String sub;
    private String name;
    private String picture;
    private String email;
    private String locale;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("hd")
    private String hostDomain;

    @Override
    public String code() {
        return this.sub;
    }
}
