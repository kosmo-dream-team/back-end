package com.dream_on.springboot.domain;

import java.sql.Date;  // 또는 java.sql.Timestamp / LocalDateTime 등 선택
import lombok.Data;

/**
 * DB의 'user' 테이블과 1:1 매핑되는 엔티티 클래스
 *
 * user 테이블 구조 (예시):
 *  - user_id (PK, auto_increment)
 *  - email (varchar(100))
 *  - password_hash (varchar(200))
 *  - user_name (varchar(50))
 *  - gender (varchar(10))
 *  - phone (varchar(15))
 *  - user_type (varchar(20))
 *  - balance (int)
 *  - created_at (datetime)
 *  - updated_at (datetime)
 *  - is_active (char(1))  'Y'/'N'
 *  - profile_image (varchar(200))
 *  - reset_token (varchar(200))
 *  - reset_token_expires_at (datetime)
 */
@Data
public class UserEntity {

    private Long userId;               // user_id (PK, auto_increment)
    private String email;              // email
    private String passwordHash;       // password_hash
    private String userName;           // user_name
    private String gender;             // gender
    private String phone;              // phone
    private String userType;           // user_type (donor/applicant/admin 등)
    private Integer balance;           // balance (보유 금액)
    private Date createdAt;            // created_at
    private Date updatedAt;            // updated_at
    private String isActive;           // 'Y'/'N'
    private String profileImage;       // 프로필 이미지 경로
    private String resetToken;         // 임시 토큰
    private Date resetTokenExpiresAt;  // 토큰 만료 시점
}
