package com.hong.smartref.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${naver_mail_id}")
    private String mailId;

    @Value("${naver_mail_pw}")
    private String mailPw;

    @Bean
    public JavaMailSender naverMailService() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(465);

        // ✅ 반드시 이메일 전체
        mailSender.setUsername(mailId);

        // ✅ 반드시 네이버 앱 비밀번호
        mailSender.setPassword(mailPw);

        mailSender.setJavaMailProperties(mailProperties());
        return mailSender;
    }

    private Properties mailProperties() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");

        // ✅ 465는 SSL 전용
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "false");

        props.put("mail.smtp.ssl.trust", "smtp.naver.com");
        props.put("mail.debug", "true");
        return props;
    }
}