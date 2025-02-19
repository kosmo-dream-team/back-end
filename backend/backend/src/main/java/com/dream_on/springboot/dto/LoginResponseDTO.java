package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 로그인 성공 시 프론트엔드에 반환할 JSON 구조
 * {
 *   "user_name": "이름",
 *   "password_hash": "비번번호",
 *   "email": "이메일@naver",
 *   "phone": "01012345678",
 *   "gender": "남자",
 *   "user_type": "applicant",
 *   "rank": "골드",
 *   "total_donation_count": 100000,
 *   "profile_image": "이미지경로"
 * }
 */
@Data
public class LoginResponseDTO {
	private long user_id;             // 사용자 id (키값)
	private String user_name;             // 사용자 이름
    private String password_hash;         // (주의) 실제 비번 해시
    private String email;                 // 이메일
    private String phone;                 // 전화번호
    private String gender;                // 성별
    private String user_type;             // donor/applicant/admin 등
    private String rank;                  // "골드", "실버" 등
    private int total_donation_count;     // 총 기부액 (예: 100000)
    private String profile_image;         // 프로필 이미지 경로
}
