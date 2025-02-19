package com.dream_on.springboot.dto;

import lombok.Data;

import java.sql.Date;

/**
 * UserDTO
 *
 * <p>프론트엔드와 통신할 때 사용하는 사용자 데이터 전송 객체(DTO).
 * 실제 DB 컬럼 전부를 다 포함할 수도 있고, 필요한 필드만 선택할 수도 있습니다.</p>
 */
@Data
public class UserDTO {
    private int user_id;           // PK
    private String email;          // 이메일
    private String password_hash;  // 비밀번호(해시 전/후)
    private String user_name;      // 사용자 이름
    private String gender;         // 성별
    private String phone;          // 전화번호
    private String user_type;      // donor/applicant/admin 등
    private int balance;           // 보유 금액
    private Date created_at;       // 가입 일자
    private Date updated_at;       // 수정 일자
    private String is_active;      // 'Y'/'N'
    private String profile_image;  // 프로필 이미지 경로

    // reset_token, reset_token_expires_at 등 필요 시 추가
}
