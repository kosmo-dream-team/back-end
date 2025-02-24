package com.dream_on.springboot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * ProjectService
 *
 * <p>
 * 프로젝트와 관련된 전반적인 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * - 전체 카테고리를 대상으로 한 기부액 상위 10개 프로젝트 조회
 * - 최근 등록된 프로젝트 3개 조회
 * - 프로젝트 상세 조회 (기부자 목록 포함)
 * - 기부(결제) 처리
 * - 프로젝트 생성, 수정
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;
    
    // 파일 업로드 디렉토리 (application.properties 파일에 file.upload-dir 속성으로 지정)
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 최근 신설된 프로젝트 3개 조회
     *
     * @return 최근 신설된 프로젝트 3개 정보를 담은 RecentProjectDTO 리스트
     */
    public List<RecentProjectDTO> getLatest3Projects() {
        return projectMapper.findLatest3Projects();
    }    
    
    /**
     * 프로젝트 상세 정보 조회
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
     * @param userId 기부자 식별자
     * @param projectId 기부 대상 프로젝트 식별자
     * @param amount 기부 금액
     * @param paymentMethod 결제 방식 (예: 카드, 계좌이체 등)
     * @throws RuntimeException 기부 내역 저장에 실패하면 예외 발생
     */
    public void donate(Long userId, Long projectId, int amount, String paymentMethod) {
        int rowCount = projectMapper.insertDonation(userId, projectId, amount, paymentMethod);
        if(rowCount <= 0) {
            throw new RuntimeException("기부 내역 저장 실패");
        }
    }    
    
    /**
     * 새로운 프로젝트 생성 (파일 업로드 포함)
     *
     * <p>
     * ProjectEntity 객체를 받아 업로드된 파일을 지정된 디렉토리에 저장하고,
     * 파일 경로 또는 파일명을 업데이트한 후 DB에 새로운 프로젝트 레코드를 추가합니다.
     * </p>
     *
     * @param project 생성할 프로젝트 엔티티
     * @throws IOException 파일 저장 과정에서 문제가 발생하면 예외 발생
     */
    public void createProject(ProjectEntity project) throws IOException {
        MultipartFile file = project.getProjectImageFile();
        if (file != null && !file.isEmpty()) {
            // 저장할 디렉토리 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 원본 파일명으로부터 확장자 추출 후 UUID로 새로운 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + ext;
            
            // 파일 저장
            File destination = new File(uploadDir + File.separator + newFilename);
            file.transferTo(destination);
            
            // 엔티티의 projectImage 필드에 파일명 저장 (필요에 따라 전체 경로나 URL을 저장할 수도 있음)
            project.setProjectImage(newFilename);
        }
        
        // 파일 처리 후 프로젝트 정보 DB 저장
        projectMapper.insertProject(project);
    }

    /**
     * 프로젝트 정보 수정
     *
     * @param project 수정할 프로젝트 엔티티
     */
    public void updateProject(ProjectEntity project) {
        projectMapper.updateProject(project);
    }    

    /**
     * (전체 카테고리) 기부액 상위 10개 프로젝트 조회
     *
     * @return ProjectSummaryDTO 리스트
     */
    public List<ProjectSummaryDTO> getTop10Projects() {
        return projectMapper.findTop10ProjectsByDonation();
    }
    
    // 전체 프로젝트 목록 조회
    public List<ProjectEntity> getAllProjects() {
        return projectMapper.findAll();
    }
    
    // 특정 프로젝트 조회
    public ProjectEntity getProjectById(int projectId) {
        return projectMapper.findById(projectId);
    }
    
    // 프로젝트 상태 업데이트 (승인/거절)
    public ProjectEntity updateProjectStatus(int projectId, String status) {
        ProjectEntity project = projectMapper.findById(projectId);
        if (project != null) {
            project.setStatus(status);
            projectMapper.update(project);
        }
        return project;
    }
    
    public int deleteProject(int projectId) {
        return projectMapper.deleteProject(projectId);
    }

    
}
