package com.hong.smartref.service;

import com.hong.smartref.config.MailService;
import com.hong.smartref.config.jwt.JwtTokenUtil;
import com.hong.smartref.data.dto.user.*;
import com.hong.smartref.data.entity.Storage;
import com.hong.smartref.data.entity.User;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long REFRESH_TOKEN_TTL =
            60L * 24 * 60 * 60; // 60일
    private final MailService mailService;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<User> existUser = userRepository.findByEmail(signupRequest.getEmail());

        if (existUser.isPresent()) {
            throw new CustomException(ErrorCode.EXIST_USER);
        }
        User user = User.create(
            signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));

        User resultUser = userRepository.save(user);

        //디폴트 냉장고 생성

    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_EQUALS_PASSWORD);
        }

        if (!user.isValid()) {
            throw new CustomException(ErrorCode.NOT_VALIDATE_USER);
        }
        String accessToken = jwtTokenUtil.createAccessToken(user);
        String refreshToken = jwtTokenUtil.createRefreshToken();
        saveRefreshToken(user.getEmail(), refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .validateTime(ZonedDateTime.now(ZoneId.of("UTC"))
                        .plusHours(1L)
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .email(user.getEmail())
                .build();
    }

    public ReissueResponse reissueAccessToken(String email, HttpServletRequest request) {
        String normalizedEmail = email.toLowerCase();
        String redisKey = "refresh:email:" + normalizedEmail;

        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1️⃣ Redis에서 Refresh Token 조회
        String savedRefreshToken = redisTemplate.opsForValue().get(redisKey);

        if (savedRefreshToken == null ||
                !savedRefreshToken.equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_SIGNATURE_TOKEN);
        }

        // 2️⃣ 사용자 조회
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_VALIDATE_USER)
                );

        // 3️⃣ Access Token 재발급
        String newAccessToken =
                jwtTokenUtil.createAccessToken(user);

        // 4️⃣ (선택) Refresh Token 회전
        // String newRefreshToken = jwtTokenUtil.createRefreshToken();
        // redisTemplate.opsForValue().set(
        //     redisKey,
        //     newRefreshToken,
        //     REFRESH_TOKEN_TTL,
        //     TimeUnit.SECONDS
        // );

        return new ReissueResponse(newAccessToken);
    }

    public String userCertificationSend (String email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        // 1️⃣ 메일 전송 & 코드 생성
        String code = mailService.sendSimpleMessage(user.getEmail());

        // 2️⃣ Redis 저장 (5분)
        String redisKey = "email:cert:" + user.getEmail();
        redisTemplate.opsForValue().set(
                redisKey,
                code,
                5,
                TimeUnit.MINUTES
        );

        return code; // (보통은 반환 안 함, 테스트용이면 OK)
    }

    public void userCertification (MailCheckRequest mailCheckRequest) {
        User user = userRepository.findByEmail(mailCheckRequest.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        String redisKey = "email:cert:" + user.getEmail();

        // 1️⃣ Redis에서 코드 조회
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            throw new CustomException(ErrorCode.EXPIRED_CERT_CODE);
        }

        // 2️⃣ 코드 비교
        if (!savedCode.equals(mailCheckRequest.getCode())) {
            throw new CustomException(ErrorCode.INVALID_CERT_CODE);
        }

        // 3️⃣ 인증 성공 → Redis 삭제
        redisTemplate.delete(redisKey);

        // 4️⃣ 유저 인증 완료
        user.setValid(true);
        userRepository.save(user);
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
