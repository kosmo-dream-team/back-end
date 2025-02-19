package com.dream_on.springboot.service;

import java.sql.Date;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder; // BCrypt

    // 1) 비밀번호 재설정 링크 발송
    public void sendResetLink(String email) {
        // 사용자 조회
        UserEntity user = userMapper.findByEmail(email);
        if(user == null) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

        // 토큰 생성 (임의 UUID)
        String token = UUID.randomUUID().toString();
        // 만료 시간 (30분 후)
        Date expires = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // DB에 reset_token, reset_token_expires 업데이트
        userMapper.updateResetToken(user.getUserId(), token, expires);

        // 메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[DreamOn] 비밀번호 재설정 안내");
        message.setText(
          "비밀번호를 재설정하려면 아래 링크를 클릭하세요 (30분 내 유효)\n" +
          "http://localhost:8080/user/reset-password?token=" + token
        );

        mailSender.send(message);
    }

    // 2) 토큰 검증 후, 새 비밀번호 설정
    public void resetPassword(String token, String newPassword) {
        // DB에서 reset_token = token, 만료 확인
        UserEntity user = userMapper.findByResetToken(token);
        if(user == null) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다.");
        }

        // 새 비밀번호 해시
        String hash = passwordEncoder.encode(newPassword);

        // update password, clear token
        int rowCount = userMapper.updatePasswordAndClearToken(user.getUserId(), hash);
        if(rowCount <= 0) {
            throw new RuntimeException("비밀번호 재설정 실패");
        }
    }
}
