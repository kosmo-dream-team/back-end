package com.dream_on.springboot.domain;

import java.sql.Date;

import lombok.Data;

// DB의 'user' 테이블과 1:1 매핑되는 POJO/Entity
@Data
public class UserEntity {
    private Long userId;               // user_id
    private String email;              // email
    private String passwordHash;       // password_hash
    private String username;           // username
    private String userType;           // user_type (donor/applicant/admin)
    private String rank;               // rank (브론즈/실버/골드)
    private Integer accumulatedDonation; // 누적 기부 금액
    private String isActive;           // 'Y'/'N'
    private String profileImage;       // 프로필 이미지 경로
    // created_at, updated_at 등은 DB에서 자동 세팅하거나, 별도 필드로 관리

    // new fields
    private String resetToken;
    private Date resetTokenExpires;
}
