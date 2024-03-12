package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] split = username.split(":");

        OAuthProvider provider = OAuthProvider.getOAuthProvider(split[0]);
        String code = split[1];

        // TODO : JWT payload 데이터로 유저 가져오기 (DB 접근 x)

        return memberRepository.findByCodeAndProvider(code, provider)
                .orElseThrow(()-> new OAuthInfoNullException("해당하는 회원이 없습니다."));
    }
}