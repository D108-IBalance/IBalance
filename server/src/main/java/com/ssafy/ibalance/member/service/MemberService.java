package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import com.ssafy.ibalance.member.util.GoogleOAuth2Utils;
import com.ssafy.ibalance.member.util.KakaoOAuth2Utils;
import com.ssafy.ibalance.member.util.NaverOAuth2Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final GoogleOAuth2Utils googleUtil;
    private final KakaoOAuth2Utils kakaoUtil;
    private final NaverOAuth2Utils naverUtil;


    private final MemberRepository memberRepository;

    public Member getMemberInfo(String inputProvider, String code, String redirectUri){

        OAuthProvider provider = OAuthProvider.getOAuthProvider(inputProvider);
        OAuthMemberInfo oAuthMemberInfo = getOAuthMemberInfo(provider, code, redirectUri);

        if(oAuthMemberInfo == null){
            throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
        }
        log.info("code : {}, provider : {}", oAuthMemberInfo.code(), provider);

        return memberRepository.findByCodeAndProvider(oAuthMemberInfo.code(), provider)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .code(oAuthMemberInfo.code())
                        .provider(provider)
                        .build()));
    }

    public OAuthMemberInfo getOAuthMemberInfo(OAuthProvider provider, String code, String redirectUrl) {
        log.info("코드 : {} code 로 {} 에 요청 시도", code, provider.toString());
        return switch(provider){
            case GOOGLE -> googleUtil.getUserInfo(code, redirectUrl);
            case KAKAO -> kakaoUtil.getUserInfo(code, redirectUrl);
            case NAVER -> naverUtil.getUserInfo(code, redirectUrl);
        };
    }
}
