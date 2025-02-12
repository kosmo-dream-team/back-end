package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 상위 10개 프로젝트 요약 정보 DTO
 */
@Data
public class ProjectSummaryDTO {
    private String projectName;  // 프로젝트명
    private String category;     // 카테고리
    private String goalDate;     // 목표일(마감일) (문자열 or Date)
    private int daysLeft;        // 남은 일수 (D-16 등)
    private int goalAmount;      // 목표금액
    private int donationRate;    // 도달율(%) ex: 33
    private String projectImage; // 프로젝트 이미지 URL 또는 파일명
}
