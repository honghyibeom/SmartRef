package com.hong.smartref.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.CustomExceptionDTO;
import com.hong.smartref.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    // private final RedisTemplate<String, String> redisTemplate; // 블랙리스트 사용 시

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        //log.info("🔥 JwtFilter HIT - URI: {}", request.getRequestURI());

        String uri = request.getRequestURI();

        // ✅ Swagger & OpenAPI 예외 처리
        if (uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenUtil.resolveToken(request);
        //log.info("🧪 resolved token = {}", token);

        if (token != null) {
            try {
                // 1️⃣ 토큰 검증 (서명 + 만료)
                if (!jwtTokenUtil.validateToken(token)) {
                    returnErrorResponse(ErrorCode.INVALID_SIGNATURE_TOKEN, response);
                    return;
                }

                // 2️⃣ Claims 추출
                Claims claims = jwtTokenUtil.getClaims(token);

                // 3️⃣ (선택) Redis 블랙리스트 체크
                /*
                String jti = claims.getId();
                if (redisTemplate.hasKey("blacklist:access:" + jti)) {
                    returnErrorResponse(ErrorCode.ALREADY_CHANGED_TOKEN, response);
                    return;
                }
                */

                // 4️⃣ Authentication 생성 및 등록
                setAuthentication(claims.getSubject());

            } catch (ExpiredJwtException e) {
                returnErrorResponse(ErrorCode.EXPIRED_TOKEN, response);
                return;
            } catch (JwtException | IllegalArgumentException e) {
                returnErrorResponse(ErrorCode.INVALID_SIGNATURE_TOKEN, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                jwtTokenUtil.createAuthentication(email);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void returnErrorResponse(
            ErrorCode errorCode,
            HttpServletResponse response
    ) throws IOException {

        log.error("JWT Filter Error: {}", errorCode.getErrorMessage());

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        CustomExceptionDTO responseDto = new CustomExceptionDTO(errorCode);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(
                objectMapper.writeValueAsString(responseDto)
        );
    }
}
