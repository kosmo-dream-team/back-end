package com.dream_on.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * ProjectService
 *
 * <p>프로젝트와 관련된 전반적인 비즈니스 로직을 처리하는 서비스 클래스입니다.
 *  - 전체 카테고리를 대상으로 한 기부액 상위 10개 프로젝트 조회
 *  - 최근 등록된 프로젝트 3개 조회
 *  - 프로젝트 상세 조회 (기부자 목록 포함)
 *  - 기부(결제) 처리
 *  - 프로젝트 생성, 수정
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;

    /**
     * (전체 카테고리) 기부액 상위 10개 프로젝트 조회
     *
     * <p>
     * 카테고리에 상관없이, 기부액이 가장 많은 상위 10개 프로젝트의 요약 정보를 조회합니다.
     * (개별 카테고리별 상위 10개 조회는 CategoryService/Controller에서 처리)
     * </p>
     *
     * @return ProjectSummaryDTO 리스트 (프로젝트명, 카테고리ID, 목표일, 남은 일수, 목표금액, 도달율, 이미지, 좋아요 수 등)
     */
    public List<ProjectSummaryDTO> getTop10Projects() {
        return projectMapper.findTop10ProjectsByDonation();
    }

    /**
     * 최근 신설된 프로젝트 3개 조회
     *
     * <p>
     * 최신 순으로 등록된 프로젝트 3개의 정보를 조회하여 전달합니다.
     * 전달 항목:
     *  - 프로젝트 식별자, 제목, 카테고리ID, 생성자 ID, 목표금액, 상세 설명
     *  - 해당 프로젝트의 총 기부액 (donationSum)
     *  - 좋아요 수 (likeCount)
     * </p>
     *
     * @return 최근 신설된 프로젝트 3개 정보를 담은 RecentProjectDTO 리스트
     */
    public List<RecentProjectDTO> getLatest3Projects() {
        return projectMapper.findLatest3Projects();
    }    
    
    /**
     * 프로젝트 상세 정보 조회
     *
     * <p>
     * 특정 프로젝트의 상세 정보를 조회합니다.
     * 전달 항목:
     *  - 프로젝트 기본 정보: 식별자, 제목, 카테고리ID, 시작일, 마감일, 상세 설명
     *  - 진행 상황: 남은 일수, 목표금액, 도달율
     *  - 기부 관련 집계: 기부자 수, 누적 기부액
     *  - 시각적 정보: 프로젝트 이미지, 좋아요 수
     *  - 기부자 목록: 해당 프로젝트에 기부한 사용자 닉네임/이름 목록
     * </p>
     *
     * @param projectId 조회할 프로젝트의 식별자
     * @return ProjectDetailDTO (상세 정보 + 기부자 목록)
     * @throws RuntimeException 프로젝트가 존재하지 않으면 예외 발생
     */
    public ProjectDetailDTO getProjectDetail(Long projectId) {
        ProjectDetailDTO detail = projectMapper.findProjectDetail(projectId);
        if(detail == null) {
            throw new RuntimeException("프로젝트가 존재하지 않습니다. id=" + projectId);
        }
        // 기부자 목록 조회 (기부자 닉네임/이름)
        List<String> donors = projectMapper.findDonorsByProjectId(projectId);
        detail.setDonors(donors);
        return detail;
    }

    /**
     * 기부(결제) 처리 로직
     *
     * <p>
     * 사용자가 특정 프로젝트에 기부한 내역을 donation 테이블에 INSERT합니다.
     * 파라미터:
     *  - userId: 기부자 식별자
     *  - projectId: 기부 대상 프로젝트 식별자
     *  - amount: 기부 금액
     *  - paymentMethod: 결제 방식 (예: 카드, 계좌이체 등)
     * </p>
     *
     * @param userId 기부자 식별자
     * @param projectId 기부 대상 프로젝트 식별자
     * @param amount 기부 금액
     * @param paymentMethod 결제 방식
     * @throws RuntimeException 기부 내역 저장에 실패하면 예외 발생
     */
    public void donate(Long userId, Long projectId, int amount, String paymentMethod) {
        int rowCount = projectMapper.insertDonation(userId, projectId, amount, paymentMethod);
        if(rowCount <= 0) {
            throw new RuntimeException("기부 내역 저장 실패");
        }
    }    
    
    /**
     * 새로운 프로젝트 생성
     *
     * <p>
     * ProjectEntity 객체를 받아 DB에 새로운 프로젝트 레코드를 추가합니다.
     * </p>
     *
     * @param project 생성할 프로젝트 엔티티
     */
    public void createProject(ProjectEntity project) {
        projectMapper.insertProject(project);
    }

    /**
     * 프로젝트 정보 수정
     *
     * <p>
     * 업데이트할 프로젝트 정보를 포함한 ProjectEntity 객체를 받아 기존 레코드를 수정합니다.
     * </p>
     *
     * @param project 수정할 프로젝트 엔티티
     */
    public void updateProject(ProjectEntity project) {
        projectMapper.updateProject(project);
    }    
}
