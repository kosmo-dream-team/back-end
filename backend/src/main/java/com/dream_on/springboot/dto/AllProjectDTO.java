package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class AllProjectDTO {
    private int project_id;          // 프로젝트 PK
    private String user_name;        // 작성자(수혜자) 이름
    private int d_day;               // 남은 일수
    private String category;         // 카테고리 명
    private String title;            // 프로젝트 제목
    private String project_image;    // 프로젝트 이미지 경로
    private int progress;            // 모금 진행도(%)
    private int target_amount;       // 목표금액
    private int like_count;          // 좋아요 수
    private String start_date;       // 시작일(문자열)
    private String status;           // 상태(예: active, pending 등)
}
