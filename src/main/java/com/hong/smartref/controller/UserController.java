package com.hong.smartref.controller;

import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.user.LoginRequest;
import com.hong.smartref.data.dto.user.LoginResponse;
import com.hong.smartref.data.dto.user.SignupRequest;
import com.hong.smartref.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Validated SignupRequest userSignupRequest) {
        userService.signup(userSignupRequest);
        return ResponseEntity.ok(
                ApiResponse.success("이메일 인증을 해야합니다.")
        );
    }

    //인증 메일 전송
    @PostMapping("/auth/send/mail/{email}")
    public ResponseEntity<ApiResponse<String>> userCertificationSend(@PathVariable("email") String email) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(
                ApiResponse.success("메일 전송", userService.userCertificationSend(email))
        );
    }

    //메일 인증
    @PostMapping("/auth/valid/mail/{email}")
    public ResponseEntity<ApiResponse<Void>> userCertification(@PathVariable("email") String email) {
        userService.userCertification(email);
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
}
