package com.ssafy.ibalance.auth.config;

import com.ssafy.ibalance.auth.type.JwtCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private String secretKey;

    public JwtTokenProvider(UserDetailsService userDetailsService, @Value("${jwt.secret.key}") String secretKey) {
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
    }

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
}
