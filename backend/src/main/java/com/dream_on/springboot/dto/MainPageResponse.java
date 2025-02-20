package com.dream_on.springboot.dto;

import lombok.Data;
import java.util.List;

@Data
public class MainPageResponse {
    // 로그인한 사용자의 정보 (로그인하지 않은 경우 null)
    private LoginInfoDTO loginInfo;
    
    // 기부 상위 10개 프로젝트 정보 (ProjectSummaryDTO에는 프로젝트명, 카테고리, 목표일, 남은 일수, 목표금액, 도달율, 프로젝트 이미지 등의 필드가 포함됨)
    private List<ProjectSummaryDTO> topProjects;

    
    // 최근 신설된 프로젝트 3개 정보
    private List<RecentProjectDTO> recentProjects;
}
