package com.dream_on.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /*
	카데고리 상세보기
	프로젝트 기부액 상위 값 10건 전달 :
	프로젝트명, 카테고리, 목표일, 마감일까지 남은 일수, 목표금액, 도달율
	*/
    @GetMapping("/top10")
    public List<ProjectSummaryDTO> getTop10Projects() {
        // 기부액 가장 많은 상위 10개 프로젝트 조회
        return projectService.getTop10Projects();
    }

    /*
	캠페인(기부-상세보기)
	해당 프로젝트 값 전달 :
	프로젝트 명칭, 카데고리, 시작일, 마감일, 설명,
	마감일까지 남은 일수(예: D-16), 목표금액, 도달율(예: 33%)
	해당 프로젝트의 기부자    
	*/


    /**
     * 프로젝트 상세 페이지 조회 (GET /project/{id})
     */
    @GetMapping("/{id}")
    public ProjectDetailDTO getProjectDetail(@PathVariable("id") Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * 기부(결제) 처리 (POST /project/{id}/donate)
     * 예: userId, amount, paymentMethod 등의 파라미터를 전달
     */
    @PostMapping("/{id}/donate")
    public String donate(@PathVariable("id") Long projectId,
                         @RequestParam Long userId,
                         @RequestParam int amount,
                         @RequestParam String paymentMethod) {
        projectService.donate(userId, projectId, amount, paymentMethod);
        return "기부 성공!";
    }
    
    
}
