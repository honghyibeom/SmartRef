package com.hong.smartref.service;

import com.hong.smartref.config.MailService;
import com.hong.smartref.config.jwt.JwtTokenUtil;
import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.user.LoginRequest;
import com.hong.smartref.data.dto.user.LoginResponse;
import com.hong.smartref.data.dto.user.ReissueResponse;
import com.hong.smartref.data.dto.user.SignupRequest;
import com.hong.smartref.data.entity.User;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender mailSender;
    private String ePw;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<User> existUser = userRepository.findByEmail(signupRequest.getEmail());

        if (existUser.isPresent()) {
            throw new CustomException(ErrorCode.EXIST_USER);
        }
        User user = User.create(
            signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
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

    public ReissueResponse reissueAccessToken(
            String email,
            String refreshToken
    ) {
        String normalizedEmail = email.toLowerCase();
        String redisKey = "refresh:email:" + normalizedEmail;

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
        return mailService.sendSimpleMessage(user.getEmail());
    }

    public void userCertification (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));
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
