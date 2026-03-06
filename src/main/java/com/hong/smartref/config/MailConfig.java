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

        // 🔥 465 → 587 변경
        mailSender.setPort(587);

        mailSender.setUsername(mailId);
        mailSender.setPassword(mailPw);

        mailSender.setJavaMailProperties(mailProperties());
        return mailSender;
    }

    private Properties mailProperties() {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");

        // 🔥 STARTTLS 사용
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "false");

        props.put("mail.smtp.ssl.trust", "smtp.naver.com");

        // 🔥 무한대기 방지
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        props.put("mail.debug", "true");

        return props;
    }
}