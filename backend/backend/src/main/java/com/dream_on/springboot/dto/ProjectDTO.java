package com.dream_on.springboot.dto;

import lombok.Data;

import java.sql.Date;

/**
 * ProjectDTO
 *
 * <p>이 DTO는 프로젝트 등록/수정/조회 등의 기본적인 CRUD 작업에서 주로 사용됩니다.
 * DB 테이블 구조와 유사하게 구성하여, 프로젝트 엔티티와의 매핑에 편리하도록 설계되었습니다.</p>
 * <ul>
 *   <li><b>프로젝트 식별자</b>: project_id</li>
 *   <li><b>프로젝트 기본 정보</b>: title, category, user_id, target_amount 등</li>
 *   <li><b>상태 및 날짜</b>: start_date, end_date, status, created_at, updated_at 등</li>
 *   <li><b>기타</b>: approval_date (예: 관리자 승인일), likeCount (좋아요 수) 등</li>
 * </ul>
 * <p>주로 Form 전송이나 간단한 목록 조회 시 활용하며, 프론트엔드에서 프로젝트 등록/수정 요청을 보낼 때도
 * 이 DTO를 사용합니다.</p>
 */
@Data
public class ProjectDTO {
    private int project_id;         // 프로젝트 식별자 (DB column: project_id)
    private String title;           // 프로젝트명
    private String category;        // 카테고리 (문자열 or ID, 필요에 따라 결정)
    private int user_id;            // 프로젝트 생성자
    private int target_amount;      // 목표금액
    private Date start_date;        // 시작일
    private Date end_date;          // 마감일
    private String status;          // 상태 (예: 'pending', 'active', 'closed' 등)
    private String description;     // 프로젝트 설명
    private Date created_at;        // 생성 일자
    private Date updated_at;        // 수정 일자
    private Date approval_date;     // 관리자 승인 일자

    private int likeCount;          // 좋아요 수 (DB: 'like' 컬럼을 매핑)
}
