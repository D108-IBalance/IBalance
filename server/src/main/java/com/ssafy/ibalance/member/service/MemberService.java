package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import com.ssafy.ibalance.member.util.GoogleOAuth2Utils;
import com.ssafy.ibalance.member.util.KakaoOAuth2Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final GoogleOAuth2Utils googleUtil;
    private final KakaoOAuth2Utils kakaoUtil;


    private final MemberRepository memberRepository;

    public Member getMemberInfo(String inputProvider, String code){

        OAuthProvider provider = OAuthProvider.getOAuthProvider(inputProvider);
        OAuthMemberInfo oAuthMemberInfo = getOAuthMemberInfo(provider, code);

        if(oAuthMemberInfo == null){
            throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
        }

        return memberRepository.findByCodeAndProvider(oAuthMemberInfo.code(), provider)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .code(oAuthMemberInfo.code())
                        .provider(provider)
                        .build()));
    }

    private OAuthMemberInfo getOAuthMemberInfo(OAuthProvider provider, String code) {
        return switch(provider){
            case GOOGLE -> googleUtil.getUserInfo(code);
            case KAKAO -> kakaoUtil.getKakaoInfo(code);
            default -> throw new IllegalArgumentException("잘못된 요청입니다.");
        };
    }
}
