package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectSummaryDTO {
    // === 프론트에서 요구하는 필드/변수명 그대로 선언 ===
    private int project_id;        // PK
    private int user_id;           // 수혜자 id
    private int category_id;       // 카테고리 id
    private String title;          // 프로젝트명
    private String project_image;  // 프로젝트 이미지 경로
    private int target_amount;     // 목표 금액

    private int d_daY;     // 모금 마감까지 남은 기간 (대문자 Y)
    private int progresS;  // 모금 진행도(%) (대문자 S)

    
/**
 * ProjectSummaryDTO
 *
 * <p>이 DTO는 상위 10개 프로젝트 등의 간단한 목록이나 요약 정보를 보여줄 때 사용됩니다.
 * 예를 들어, 홈 화면의 "기부 상위 10개 프로젝트" 섹션을 구성할 때 주로 쓰입니다.</p>
 * <ul>
 *   <li><b>프로젝트명</b>: projectName</li>
 *   <li><b>카테고리</b>: category</li>
 *   <li><b>마감일</b>: goalDate (문자열 or Date), daysLeft (남은 일수)</li>
 *   <li><b>목표금액</b>: goalAmount</li>
 *   <li><b>도달율</b>: donationRate (%)</li>
 *   <li><b>이미지</b>: projectImage (URL or 파일명)</li>
 *   <li><b>좋아요 수</b>: likeCount</li>
 * </ul>
 * <p>기본적으로 프로젝트의 핵심 요약만 보여주며, 상세 정보는 ProjectDetailDTO 등에서 확인합니다.</p>
 
@Data
public class ProjectSummaryDTO {
    private String projectName;    // 프로젝트명
    private String category;       // 카테고리 (문자열)
    private String goalDate;       // 목표일(마감일) (문자열 or Date)
    private int daysLeft;          // 남은 일수 (예: D-16)
    private int goalAmount;        // 목표금액
    private int donationRate;      // 도달율(%) ex: 33
    private String projectImage;   // 프로젝트 이미지 경로/URL

    private int likeCount;         // 좋아요 수
*/
}
