package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.user.*;
import com.hong.smartref.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입 api", description = "유저 정보를 저장")
    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Validated SignupRequest userSignupRequest) {
        userService.signup(userSignupRequest);
        return ResponseEntity.ok(
                ApiResponse.success("이메일 인증을 해야합니다.")
        );
    }

    //인증 메일 전송
    @Operation(summary = "문자 전송 api", description = "문자 인증 전송")
    @PostMapping("/auth/send/mail/{email}")
    public ResponseEntity<ApiResponse<String>> userCertificationSend(@PathVariable("email") String email) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(
                ApiResponse.success("메일 전송", userService.userCertificationSend(email))
        );
    }

    //메일 인증
    @Operation(summary = "문자 인증 api", description = "문자 인증 확인")
    @PostMapping("/auth/valid/mail")
    public ResponseEntity<ApiResponse<Void>> userCertification(@RequestBody MailCheckRequest mailCheckRequest) {
        userService.userCertification(mailCheckRequest);
        return ResponseEntity.ok(
                ApiResponse.success("인증 완료")
        );
    }

    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> userCertification(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(
                ApiResponse.success("로그인 성공", userService.login(loginRequest))
        );
    }

    @Operation(summary = "토큰 재발급", description = "토큰 재발급, refreshToken 입력")
    @PostMapping("/auth/recreate/accessToken/{email}")
    public ResponseEntity<ApiResponse<ReissueResponse>> recreateAccessToken(@PathVariable("email") String email, HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("토큰 재발급", userService.reissueAccessToken(email, request))
        );
    }
}
