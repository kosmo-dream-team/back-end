package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class LoginInfoDTO {
    private String name;      // 사용자 이름
    private String grade;     // 사용자 등급 (예: VIP, 일반 등)
    private String profile;   // 프로필 이미지 URL 또는 프로필 정보
}
