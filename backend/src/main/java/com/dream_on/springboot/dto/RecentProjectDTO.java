package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * RecentProjectDTO
 *
 * <p>최근 신설된 프로젝트 목록을 표시하기 위한 DTO입니다.
 * 예를 들어, "최근 등록된 프로젝트 3개" 같은 섹션에 사용됩니다.</p>
 * <ul>
 *   <li><b>projectId</b>: 프로젝트 식별자</li>
 *   <li><b>title</b>: 프로젝트명</li>
 *   <li><b>category</b>: 카테고리명 (문자열), 필요시 category_id 사용 가능</li>
 *   <li><b>userId</b>: 프로젝트 생성자 식별자</li>
 *   <li><b>targetAmount</b>: 목표금액</li>
 *   <li><b>description</b>: 프로젝트 간단 설명</li>
 *   <li><b>donationSum</b>: 해당 프로젝트에 기부된 총 금액 (donation_amount 합계)</li>
 *   <li><b>likeCount</b>: 좋아요 수</li>
 * </ul>
 * <p>상세 정보가 필요한 경우 ProjectDetailDTO 등을 통해 추가 정보를 조회합니다.</p>
 */
@Data
public class RecentProjectDTO {
    private int projectId;       // 프로젝트 식별자
    private String title;        // 프로젝트명
    private String category;     // 카테고리 (문자열)
    private int userId;          // 프로젝트 생성자 ID
    private int targetAmount;    // 목표금액
    private String description;  // 프로젝트 설명
    private int donationSum;     // 기부된 총 금액 (donation_amount 합계)

    private int likeCount;       // 좋아요 수
}
