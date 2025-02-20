package com.dream_on.springboot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.service.ProjectService;

import lombok.RequiredArgsConstructor;

/**
 * ProjectController
 *
 * <p>프로젝트 관련 API를 제공하는 컨트롤러입니다.
 *  - 전체 카테고리를 대상으로 한 상위 10개 프로젝트 조회
 *  - 프로젝트 상세 조회
 *  - 기부(결제) 처리
 *  - 프로젝트 생성/수정
 *  
 *  (개별 카테고리에 대한 상위 10개 프로젝트는 CategoryController에서 처리)
 * </p>
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * (전체 카테고리) 기부액 상위 10개 프로젝트 조회
     *
     * <p>
     * 기부액이 가장 많은 상위 10개 프로젝트를 반환합니다.
     * 카테고리 구분 없이 전체 프로젝트 중 상위 10개만 조회합니다.
     * (개별 카테고리는 CategoryController를 참조)
     * </p>
     *
     * @return ProjectSummaryDTO 리스트
     */
    @GetMapping("/top10")
    public ResponseEntity<List<ProjectSummaryDTO>> getTop10Projects() {
        List<ProjectSummaryDTO> topProjects = projectService.getTop10Projects();
        return ResponseEntity.ok(topProjects);
    }


    /**
     * 최근 등록된 프로젝트 3개
     *
     * <p>
     * (카테고리 구분 없이) 최근 등록된 프로젝트 3개의 정보를 조회합니다.
     * 필요 시, 다른 컨트롤러에서 처리해도 되지만, 
     * 여기서는 ProjectService에 메서드가 있으므로 간단히 추가했습니다.
     * </p>
     *
     * @return List<RecentProjectDTO>
     */
    @GetMapping("/latest")
    public ResponseEntity<List<RecentProjectDTO>> getLatestProjects() {
        List<RecentProjectDTO> latestProjects = projectService.getLatest3Projects();
        return ResponseEntity.ok(latestProjects);
    }    
    
    /**
     * 프로젝트 상세 정보 조회
     *
     * <p>
     * 특정 프로젝트의 상세 정보를 반환합니다.
     * </p>
     *
     * @param projectId 조회할 프로젝트 식별자
     * @return ProjectDetailDTO (프로젝트 기본 정보, 진행 상황, 기부 집계, 이미지, 좋아요 수, 기부자 목록 포함)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectDetail(@PathVariable("id") Long projectId) {
    	ProjectDetailDTO detail = projectService.getProjectDetail(projectId);
        return ResponseEntity.ok(detail);
    }

    /**
     * 기부(결제) 처리
     *
     * <p>
     * 사용자가 특정 프로젝트에 기부할 때 기부 내역을 저장합니다.
     * 파라미터:
     *  - userId: 기부자 식별자
     *  - amount: 기부 금액
     *  - paymentMethod: 결제 방식 (예: 카드, 계좌이체)
     * </p>
     *
     * @param projectId 기부 대상 프로젝트 식별자
     * @param userId 기부자 식별자
     * @param amount 기부 금액
     * @param paymentMethod 결제 방식
     * @return 성공 메시지 ("기부 성공!")
     */
    @PostMapping("/{id}/donate")
    public ResponseEntity<String> donate(@PathVariable("id") Long projectId,
                                         @RequestParam Long userId,
                                         @RequestParam int amount,
                                         @RequestParam String paymentMethod) {
        projectService.donate(userId, projectId, amount, paymentMethod);
        return ResponseEntity.ok("기부 성공!");
    }

    /**
     * 프로젝트 생성
     *
     * <p>
     * 프론트엔드에서 전달받은 프로젝트 정보를 기반으로 새로운 프로젝트를 생성합니다.
     * </p>
     *
     * @param project 생성할 프로젝트 정보를 담은 ProjectEntity
     * @return 성공 메시지 ("프로젝트가 생성되었습니다.")
     */
    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectEntity project) {
        projectService.createProject(project);
        return ResponseEntity.ok("프로젝트가 생성되었습니다.");
    }

    /**
     * 프로젝트 수정
     *
     * <p>
     * 특정 프로젝트의 정보를 수정합니다.
     * URL 경로에서 프로젝트 식별자를 받고, 본문에 수정할 데이터를 담은 ProjectEntity를 전달합니다.
     * </p>
     *
     * @param projectId 수정할 프로젝트 식별자
     * @param project 수정할 프로젝트 정보를 담은 ProjectEntity
     * @return 성공 메시지 ("프로젝트가 수정되었습니다.")
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") int projectId,
                                                @RequestBody ProjectEntity project) {
        project.setProjectId(projectId);
        projectService.updateProject(project);
        return ResponseEntity.ok("프로젝트가 수정되었습니다.");
    }

}
