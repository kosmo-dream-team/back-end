package com.dream_on.springboot.dto;

import lombok.Data;

import java.util.List;

/**
 * ProjectDetailDTO
 *
 * <p>이 DTO는 프로젝트 상세 페이지에서 필요한 모든 정보를 담기 위해 사용됩니다.</p>
 * <ul>
 *   <li><b>프로젝트 기본 정보</b>: 프로젝트 식별자, 제목, 카테고리, 시작일, 마감일, 상세 설명 등</li>
 *   <li><b>진행 현황</b>: 남은 일수(daysLeft), 목표 금액(targetAmount), 기부 도달율(donationRate)</li>
 *   <li><b>기부자 목록</b>: 프로젝트에 기부한 사용자들의 닉네임/이름 목록</li>
 *   <li><b>추가 정보</b>: 기부자 수(donorCount), 누적 기부액(accumulatedDonation), 프로젝트 이미지, 좋아요 수(likeCount) 등</li>
 * </ul>
 * <p>주로 프로젝트 상세 화면에서 사용되며, 기부 현황과 프로젝트 정보를 한 번에 보여주기 위해 백엔드에서 이 DTO를
 * 생성해 프론트엔드로 전달합니다.</p>
 */
@Data
public class ProjectDetailDTO {
    private int projectId;           // 프로젝트 식별자
    private String title;            // 프로젝트명
    private String category;         // 카테고리 (문자열)
    private String startDate;        // 시작일 (문자열 or Date)
    private String endDate;          // 마감일 (문자열 or Date)
    private String description;      // 상세 설명

    private int daysLeft;            // 남은 일수 (예: D-16)
    private int targetAmount;        // 목표금액
    private int donationRate;        // 도달율 (예: 33%)

    private List<String> donors;     // 기부자 목록(간단히 닉네임/이름만 전달)
    private int donorCount;          // 기부자 수 (중복 제거된 count)
    private int accumulatedDonation; // 누적 기부액 (donation_amount 합계)
    private String projectImage;     // 프로젝트 이미지 경로/URL

    private int likeCount;           // 좋아요(‘좋아요’ 수) 
}
