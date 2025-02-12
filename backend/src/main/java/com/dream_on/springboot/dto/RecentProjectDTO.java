package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 최근 신설된 프로젝트 정보 DTO
 */
@Data
public class RecentProjectDTO {
    private Long projectId;       // 프로젝트 식별자
    private String title;         // 프로젝트명
    private String category;      // 카테고리
    private Long userId;          // 프로젝트 생성자 ID
    private int targetAmount;     // 목표금액
    private String description;   // 상세 설명
    private int donationSum;      // 해당 프로젝트에 기부된 총 금액 (donation_amount 합계)
}
