package com.skhu.skhucapstone.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("[SKHU] 학교 이메일 인증번호"); // 이메일 제목 <추후 변경 예정>
        message.setText("인증번호: " + code + "\n\n인증번호는 5분 후 만료됩니다."); // 이메일 본문
        mailSender.send(message);
    }
}
