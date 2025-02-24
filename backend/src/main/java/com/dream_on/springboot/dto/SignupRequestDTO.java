package com.dream_on.springboot.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 시, 프론트엔드에서 보내주는 JSON 형식과 매핑되는 DTO
 */
@Getter
@Setter
public class SignupRequestDTO {
    private String email;          // 이메일
    private String user_name;      // 이름
    private String password_hash;  // 비밀번호
    private String phone;          // 전화번호
    private String gender;         // 성별
    private String user_type;      // "donor" or "applicant" (기부자 / 수혜자)
}
