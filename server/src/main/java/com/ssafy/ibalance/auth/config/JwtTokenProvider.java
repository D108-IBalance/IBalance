package com.ssafy.ibalance.auth.config;

import com.google.common.net.HttpHeaders;
import com.ssafy.ibalance.auth.response.JwtTokenResponse;
import com.ssafy.ibalance.auth.type.JwtCode;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.entity.RefreshToken;
import com.ssafy.ibalance.member.exception.TokenInvalidException;
import com.ssafy.ibalance.member.repository.RefreshTokenRedisRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import com.ssafy.ibalance.member.type.Role;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
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

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final RefreshTokenRedisRepository redisRepository;
    private String secretKey;

    public JwtTokenProvider(UserDetailsService userDetailsService,
                            RefreshTokenRedisRepository redisRepository,
                            @Value("${jwt.secret.key}") String secretKey) {
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
        this.redisRepository = redisRepository;
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

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserInfoClaim(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserInfoClaim(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("provider") + ":" + claims.getSubject();
    }

    public void setRefreshTokenForClient(HttpServletResponse response, Member member) {
        String refreshToken = makeRefreshToken(member.getCode(), member.getProvider());

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge((int) (refreshTokenValidTime / 1000));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        redisRepository.save(RefreshToken.builder()
                .id(member.getId())
                .refreshToken(refreshToken)
                .build());

        response.addCookie(cookie);
    }

    public void removeRefreshTokenForClient(HttpServletRequest request, HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("refreshToken", null)
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        if(request.getCookies() != null && request.getCookies().length != 0) {
            Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("refreshToken"))
                    .findFirst()
                    .ifPresent(c -> redisRepository.deleteByRefreshToken(c.getAttribute("refreshToken")));
        }

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

    public JwtTokenResponse reissueAccessToken(String refreshToken) {
        if(isNotValidRefreshToken(refreshToken)) {
            throw new TokenInvalidException("refresh token 이 유효하지 않습니다.");
        }

        Member foundMember = (Member) userDetailsService.loadUserByUsername(getUserInfoClaim(refreshToken));
        return makeJwtTokenResponse(foundMember);
    }

    private boolean isNotValidRefreshToken(String refreshToken) {
        log.info("refresh token : {}", refreshToken);
        return refreshToken == null
                || redisRepository.findByRefreshToken(refreshToken).isEmpty()
                || validateToken(refreshToken) != JwtCode.ACCESS;
    }
}
