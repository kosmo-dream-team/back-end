package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 프론트엔드가 /api/login 으로 POST할 때 보내는 JSON
 * {
 *   "email": "이메일",
 *   "password_hash": "패스워드"
 * }
 */
@Data
public class LoginRequestDTO {
    private String email;         // "이메일"
    private String password_hash; // "패스워드"
}
