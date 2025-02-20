package com.dream_on.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;

@Mapper
public interface ProjectMapper {



    /**
     * 특정 카테고리에 대한 기부액 상위 10위 프로젝트
     * @param categoryId 조회할 카테고리 ID
     */
	List<ProjectSummaryDTO> findTop10ProjectsByDonationInCategory(@Param("categoryId") int categoryId);
        
    
    // 최근 신설된 프로젝트 3개 정보 조회
	List<RecentProjectDTO> findLatest3Projects();
    
    
    /*
	캠페인(기부-상세보기)
	해당 프로젝트 값 전달 :
	프로젝트 명칭, 카데고리, 시작일, 마감일, 설명,
	마감일까지 남은 일수(예: D-16), 목표금액, 도달율(예: 33%)
	해당 프로젝트의 기부자    
	*/

    // 1) 프로젝트 기본 정보 + sum(donation_amount)로 도달율 계산
    ProjectDetailDTO findProjectDetail(@Param("projectId") Long projectId);

    // 2) 기부자 목록(닉네임 등)
    List<String> findDonorsByProjectId(@Param("projectId") Long projectId);

    // (추가) 기부 INSERT
    int insertDonation(@Param("userId") Long userId,
                       @Param("projectId") Long projectId,
                       @Param("amount") int amount,
                       @Param("paymentMethod") String paymentMethod);

    // 새로운 프로젝트 레코드를 DB에 추가
    int insertProject(ProjectEntity project);

    // 기존의 프로젝트 정보를 수정
    int updateProject(ProjectEntity project);

    /*
	* 전체 카테고리에 대한 기부액 상위 10위 프로젝트
	프로젝트명, 카테고리, 목표일, 마감일까지 남은 일수, 목표금액, 도달율

    @Select("""
        SELECT 
            p.title        AS projectName,
            p.category            AS category,
            DATE_FORMAT(p.end_date, '%Y-%m-%d') AS goalDate,
            DATEDIFF(p.end_date, NOW()) AS daysLeft,
            p.target_amount       AS goalAmount,
            -- NULL 방지로 SUM(d.donation_amount)와 p.target_amount를 0 처리
            CAST( ( COALESCE(SUM(d.donation_amount), 0) / p.target_amount ) * 100 AS UNSIGNED ) AS donationRate
            p.project_image                        AS projectImage
        FROM project p
        LEFT JOIN donation d 
               ON p.project_id = d.project_id
        GROUP BY p.project_id
        ORDER BY SUM(d.donation_amount) DESC
        LIMIT 10
        """)
	*/
	List<ProjectSummaryDTO> findTop10ProjectsByDonation();
    
    
}
