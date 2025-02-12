package com.dream_on.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;

    
    
    /*
	카데고리 상세보기
	프로젝트 기부액 상위 값 10건 전달 :
	프로젝트명, 카테고리, 목표일, 마감일까지 남은 일수, 목표금액, 도달율
	*/
    public List<ProjectSummaryDTO> getTop10Projects() {
        return projectMapper.findTop10ProjectsByDonation();
    }

    // 최근 신설된 프로젝트 3개 조회
    public List<RecentProjectDTO> getLatest3Projects() {
        return projectMapper.findLatest3Projects();
    }    
    
    /*
	캠페인(기부-상세보기)
	해당 프로젝트 값 전달 :
	프로젝트 명칭, 카데고리, 시작일, 마감일, 설명,
	마감일까지 남은 일수(예: D-16), 목표금액, 도달율(예: 33%)
	해당 프로젝트의 기부자    
	*/
    // 프로젝트 상세 조회
    public ProjectDetailDTO getProjectDetail(Long projectId) {
        // 1) 기본 정보 조회 (누적 기부액, 도달율, 기부자 수, 프로젝트 이미지 포함)
        ProjectDetailDTO detail = projectMapper.findProjectDetail(projectId);
        if(detail == null) {
            throw new RuntimeException("프로젝트가 존재하지 않습니다. id=" + projectId);
        }
        // 2) 기부자 목록 조회
        List<String> donors = projectMapper.findDonorsByProjectId(projectId);
        detail.setDonors(donors);
        return detail;
    }

    // 기부하기 (결제) 로직
    public void donate(Long userId, Long projectId, int amount, String paymentMethod) {
        // 실제 결제 연동 후 DB에 기부 내역 INSERT (여기서는 단순 DB 삽입)
        int rowCount = projectMapper.insertDonation(userId, projectId, amount, paymentMethod);
        if(rowCount <= 0) {
            throw new RuntimeException("기부 내역 저장 실패");
        }
    }    
    
}
