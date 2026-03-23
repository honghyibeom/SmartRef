package com.hong.smartref.service;

import com.hong.smartref.config.jwt.JwtTokenUtil;
import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.user.*;
import com.hong.smartref.data.entity.*;
import com.hong.smartref.data.enumerate.DefaultStorageColor;
import com.hong.smartref.data.enumerate.DefaultStorageName;
import com.hong.smartref.data.enumerate.StorageTypeEnum;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.*;
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
    private final StorageRepository storageRepository;
    private final StorageUserRepository storageUserRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final StorageTypeRepository storageTypeRepository;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<User> existUser = userRepository.findByEmail(signupRequest.getEmail());

        if (existUser.isPresent()) {
            throw new CustomException(ErrorCode.EXIST_USER);
        }
        User user = User.create(
            signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));

        User resultUser = userRepository.save(user);

        //디폴트 storage 생성
        StorageType storageType = storageTypeRepository.findByStorageTypeId(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_TYPE));
        Storage storage = Storage.create(
                DefaultStorageName.getRandomStorageName(),
                DefaultStorageColor.getRandomColor(),
                storageType
        );
        storageRepository.save(storage);

        //쓰레기통 storage 생성
        StorageType storageTrashType = storageTypeRepository.findByStorageTypeId(20L)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_STORAGE_TYPE));
        Storage trashStorage = Storage.create(
                DefaultStorageName.getRandomStorageName(),
                DefaultStorageColor.getRandomColor(),
                storageTrashType
        );
        storageRepository.save(trashStorage);


        // storage User 매핑 저장
        StorageUser storageUser = StorageUser.create(resultUser, storage);
        storageUserRepository.save(storageUser);

        // trash storage User 매핑 저장
        StorageUser trashStorageUser = StorageUser.create(resultUser, trashStorage);
        storageUserRepository.save(trashStorageUser);

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

    public String userCertificationSend (EmailRequest email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        // 1️⃣ 메일 전송 & 코드 생성
        String code = mailService.sendMail(user.getEmail(), "cert");

        // 2️⃣ Redis 저장 (5분)
        String redisKey = "cert:email:" + user.getEmail();
        redisTemplate.opsForValue().set(
                redisKey,
                code,
                5,
                TimeUnit.MINUTES
        );

        return "코드 전송"; // (보통은 반환 안 함, 테스트용이면 OK)
    }

    @Transactional
    public void userCertification (MailCheckRequest mailCheckRequest) {
        User user = userRepository.findByEmail(mailCheckRequest.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        String redisKey = "cert:email:" + user.getEmail();

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

    @Transactional
    public void registerDevice(UserDeviceRequest userDeviceRequest, UserDetailsImpl userDetails) {

        Optional<UserDevice> exist = userDeviceRepository.findByFcmToken(userDeviceRequest.getFcmToken());

        if (exist.isPresent()) {
            exist.get().setUser(userDetails.getUser());
        } else {
            UserDevice device = UserDevice.builder()
                    .fcmToken(userDeviceRequest.getFcmToken())
                    .user(userDetails.getUser())
                    .deviceType(userDeviceRequest.getDeviceType())
                    .build();
            userDeviceRepository.save(device);
        }
    }

    public UserInfo getUserInfo(UserDetailsImpl userDetails) {
        return UserInfo.builder()
                .color(userDetails.getUser().getNicknameColor())
                .isPremium(userDetails.getUser().getIsPremium())
                .username(userDetails.getUser().getNickname())
                .build();
    }

    public void recreatePasswordCode(EmailRequest email) {
        // 1️⃣ 메일 전송 & 코드 생성
        String code = mailService.sendMail(email.getEmail(),"password");

        // 2️⃣ Redis 저장 (5분)
        String redisKey = "password:email:" + email.getEmail();
        redisTemplate.opsForValue().set(
                redisKey,
                code,
                5,
                TimeUnit.MINUTES
        );
    }

    public void passwordCertification (MailCheckRequest mailCheckRequest) {
        User user = userRepository.findByEmail(mailCheckRequest.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        String redisKey = "password:email:" + user.getEmail();

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

    }

    @Transactional
    public void setNewPassword(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
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
