package com.dream_on.springboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailDTO {
    private Long projectId;         // 프로젝트 식별자
    private String title;           // 프로젝트명
    private String category;        // 카테고리
    private String startDate;       // 시작일 (문자열 or Date)
    private String endDate;         // 마감일
    private String description;     // 상세 설명

    private int daysLeft;           // 남은 일수 (예: D-16)
    private int targetAmount;       // 목표금액
    private int donationRate;       // 도달율 (예: 33)

    // 기부자 목록(간단히 기부자 닉네임 or 이름만 전달)
    private List<String> donors;

    // 추가: 기부자 수, 누적 기부액, 프로젝트 이미지
    private int donorCount;         // 기부자 수 (중복 제거된 count)
    private int accumulatedDonation; // 누적 기부액 (donation_amount의 합계)
    private String projectImage;     // 프로젝트 이미지 (project_image 컬럼)
}
