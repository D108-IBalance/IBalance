package com.ssafy.ibalance.auth.config;

import com.google.common.net.HttpHeaders;
import com.ssafy.ibalance.auth.response.JwtTokenResponse;
import com.ssafy.ibalance.auth.type.JwtCode;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.type.OAuthProvider;
import com.ssafy.ibalance.member.type.Role;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private String secretKey;

    public JwtTokenProvider(UserDetailsService userDetailsService, @Value("${jwt.secret.key}") String secretKey) {
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
    }

    public static long tokenValidTime = 3 * 60 * 60 * 1000L;    // 3시간
    public static long refreshTokenValidTime = 15 * 60 * 60 * 24 * 1000L;   // 15일

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public JwtCode validateToken(String token) {
        if(token == null) {
            log.debug("JWT token이 null 입니다.");
            return JwtCode.DENIED;
        }

        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다.");
            return JwtCode.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("잘못된 JWT 토큰입니다.");
        }

        return JwtCode.DENIED;
    }

    private String makeRefreshToken(String code, OAuthProvider oAuthProvider) {
        Claims claims = Jwts.claims().setSubject(code);
        claims.put("provider", oAuthProvider.toString());

        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Redis 저장 로직 추가 필요

        return token;
    }

    private String makeAccessToken(String code, Set<Role> roles, OAuthProvider oAuthProvider) {
        Claims claims = Jwts.claims().setSubject(code);
        claims.put("roles", roles);
        claims.put("provider", oAuthProvider.toString());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPrimaryKey(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserPrimaryKey(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void setRefreshTokenForClient(HttpServletResponse response, Member member) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", makeRefreshToken(member.getCode(), member.getProvider()))
                .maxAge(refreshTokenValidTime / 1000)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        // TODO : Refresh Token 생성하고, Redis 에 저장하는 코드 생성하기
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void removeRefreshTokenForClient(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", null)
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public JwtTokenResponse makeJwtTokenResponse(Member member) {
        String accessToken = makeAccessToken(member.getCode(), member.getRoles(), member.getProvider());

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .oAuthProvider(member.getProvider())
                .build();
    }
}
