package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.response.KakaoInfoResponseDto;
import com.ssafy.ibalance.member.dto.response.KakaoTokenResponseDto;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.exception.KakaoInfoIsNullException;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.info-url}")
    private String kakaoInfoUrl;

    @Value("${kakao.token-url}")
    private String kakaoTokenUrl;

    private final MemberRepository memberRepository;

    public Member getKakaoInfo(String code) {
        log.info("getKakaoInfo 호출 : {}", code);

        String kakaoAccessToken = getKakaoToken(code);

        log.info("유저 정보 가져오기 : {}", kakaoAccessToken);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoInfoUrl)
                .defaultHeader("Authorization", "Bearer " + kakaoAccessToken)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        KakaoInfoResponseDto kakaoInfoResponseDto = webClient.get()
                .retrieve()
                .bodyToMono(KakaoInfoResponseDto.class)
                .block();

        // kakao info null 확인
        if(kakaoInfoResponseDto == null) {
            throw new KakaoInfoIsNullException("해당하는 유저가 없습니다.");
        }

        // 해당하는 id가 이미 존재하는지 확인
        boolean checkMemberExists = memberRepository.existsByCodeAndProvider(kakaoInfoResponseDto.getId().toString(), OAuthProvider.KAKAO);

        // 해당하는 id가 이미 존재하면 DB에 저장 후 member 반환
        if(!checkMemberExists) {
            Member member = Member.builder()
                    .code(kakaoInfoResponseDto.getId().toString())
                    .provider(OAuthProvider.KAKAO)
                    .build();

            memberRepository.save(member);
            return member;
        }

        return memberRepository.findByCodeAndProvider(kakaoInfoResponseDto.getId().toString(), OAuthProvider.KAKAO).get();

    }

    public String getKakaoToken(String code) throws KakaoTokenIsNullException {
        log.info("getKakaoToken 호출 : {}", code);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoTokenUrl)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);

        KakaoTokenResponseDto kakaoTokenResponseDto = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info("kakaoTokenResponseDto : {}", kakaoTokenResponseDto);

        if(kakaoTokenResponseDto != null) {
            return kakaoTokenResponseDto.getAccessToken();
        }

        throw new KakaoTokenIsNullException("kakao accessToken이 없습니다.");
    }
}
