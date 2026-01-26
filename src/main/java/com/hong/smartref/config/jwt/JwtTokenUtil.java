package com.hong.smartref.config.jwt;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.config.security.UserDetailsServiceImpl;
import com.hong.smartref.data.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenUtil {
    private final UserDetailsServiceImpl userDetailsService;
    private final SecretKey secretKey;

    public static final String AUTHORIZATION_KEY = "auth";

    public JwtTokenUtil(
            @Value("${jwt.secret.key}") String secretKey,
            UserDetailsServiceImpl userDetailsService
    ) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /* =========================
      Access Token 생성
      ========================= */
    public String createAccessToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getEmail())
                .id(UUID.randomUUID().toString())          // ⭐ jti
                .claim(AUTHORIZATION_KEY, user.getRole())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 60 * 60 * 1000L))
                .signWith(secretKey)
                .compact();
    }

    /* =========================
       Refresh Token 생성
       ========================= */
    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 60L * 24 * 60 * 60 * 1000))
                .signWith(secretKey)
                .compact();
    }

    /* =========================
       토큰 검증 (서명 + 만료)
       ========================= */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT Token", e);
            return false;
        }
    }

    /* =========================
       Claims 추출
       ========================= */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* =========================
       Authentication 생성
       ========================= */
    public Authentication createAuthentication(String email) {
        UserDetailsImpl userDetails =
                userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    /* =========================
       Authorization Header 파싱
       ========================= */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
