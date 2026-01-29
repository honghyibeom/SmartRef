package com.hong.smartref.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.smartref.config.jwt.JwtTokenUtil;
import com.hong.smartref.data.dto.user.LoginResponse;
import com.hong.smartref.data.dto.user.SocialLoginRequest;
import com.hong.smartref.data.dto.user.SocialUserInfoDTO;
import com.hong.smartref.data.entity.User;
import com.hong.smartref.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverLoginService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long REFRESH_TOKEN_TTL =
            60L * 24 * 60 * 60;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirect_uri;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String client_secret;
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String token_url;
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String user_info_url;


    public LoginResponse login(SocialLoginRequest socialLoginRequest) throws JsonProcessingException {
        //토큰 발급
        String accessToken = getAccessToken(socialLoginRequest);
        //유저 정보 수집
        SocialUserInfoDTO socialUserInfoDto = getUserInfo(accessToken);

        Optional<User> existData = userRepository.findByEmail(socialUserInfoDto.getEmail());

        User user;
        if (existData.isEmpty()) {
            user = User.create(socialUserInfoDto.getEmail(), null);
            user.setValid(true);
            String createToken = jwtUtil.createAccessToken(user);
            String refreshToken = jwtUtil.createRefreshToken();
            saveRefreshToken(user.getEmail(), refreshToken);

            return LoginResponse.builder()
                    .accessToken(createToken)
                    .refreshToken(refreshToken)
                    .validateTime(ZonedDateTime.now(ZoneId.of("UTC"))
                            .plusHours(1L)
                            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .email(user.getEmail())
                    .build();
        }
        else {
            String createToken = jwtUtil.createAccessToken(existData.get());
            String refreshToken = jwtUtil.createRefreshToken();
            saveRefreshToken(existData.get().getEmail(), refreshToken);

            return LoginResponse.builder()
                    .accessToken(createToken)
                    .refreshToken(refreshToken)
                    .validateTime(ZonedDateTime.now(ZoneId.of("UTC"))
                            .plusHours(1L)
                            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .email(existData.get().getEmail())
                    .build();
        }
    }


    public String getAccessToken(SocialLoginRequest socialLoginRequest) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        String code = socialLoginRequest.getCode();

        // 시작 부분의 큰따옴표 제거
        if (code.startsWith("\"")) {
            code = code.substring(1);
        }

        // 끝 부분의 큰따옴표 제거
        if (code.endsWith("\"")) {
            code = code.substring(0, code.length() - 1);
        }

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client_id);
        body.add("redirect_uri", redirect_uri);
        body.add("client_secret", client_secret);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                token_url,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );

        log.info(response.toString());

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private SocialUserInfoDTO getUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> TokenRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                user_info_url,
                HttpMethod.POST,
                TokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        log.info(String.valueOf(jsonNode));

        String name = jsonNode.get("response").get("nickname").asText();
        String email = jsonNode.get("response").get("email").asText();

        return new SocialUserInfoDTO(name, email);
    }

    private void saveRefreshToken(String email, String refreshToken) {
        String key = "refresh:email:" + email.toLowerCase();

        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                REFRESH_TOKEN_TTL, // 60일
                TimeUnit.SECONDS
        );
    }
}
