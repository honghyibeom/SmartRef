package com.hong.smartref.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final RestClient restClient = RestClient.create();

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${mail.from}")
    private String from;

    public String sendMail(String to, String gbn) {

        String code = generateCode();

        Map<String, Object> body = new HashMap<>();
        if (gbn.equals("cert")) {
            // 🔥 6자리 인증코드 생성
            body.put("from", from);
            body.put("to", List.of(to));
            body.put("subject", "SmartRef 이메일 인증");

            body.put("html",
                    "<h2>SmartRef 이메일 인증</h2>" +
                            "<p>아래 인증코드를 입력하세요.</p>" +
                            "<h1>" + code + "</h1>"
            );
        }
        else if (gbn.equals("password")) {
            // 🔥 6자리 인증코드 생성

            body.put("from", from);
            body.put("to", List.of(to));
            body.put("subject", "SmartRef 비밀번호 재설정 인증 코드");

            body.put("html",
                    "<h2>SmartRef 비밀번호 재설정 인증 코드</h2>" +
                            "<p>아래 인증코드를 입력하세요.</p>" +
                            "<h1>" + code + "</h1>"
            );
        }

        restClient.post()
                .uri("https://api.resend.com/emails")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(body)
                .retrieve()
                .toBodilessEntity();

        return code;
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
