package com.dream_on.springboot.dto;

import lombok.Data;

// 마이페이지 값 전달 DTO
@Data
public class MyPageResponseDTO {
    private Long userId;          // 사용자 식별자
    private String name;          // 사용자 이름
    private String email;         // 이메일
    private String profileImage;  // 프로필 이미지 경로
    private int totalDonation;    // 총 후원 금액 (donation_amount 합계)
    private String rank;          // 자동 산출되는 등급 (예: Bronze, Silver, Gold)
}
