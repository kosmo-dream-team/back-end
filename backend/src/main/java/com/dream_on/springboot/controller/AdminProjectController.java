package com.dream_on.springboot.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.service.ProjectService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/project")
@RequiredArgsConstructor
public class AdminProjectController {

    private final ProjectService projectService;

    // 1. 전체 프로젝트 목록 조회 (관리자 전용)
    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        HttpHeaders headers = new HttpHeaders();
        // 예시: "project 0-9/totalCount"
        headers.add("Content-Range", "project 0-" + (projects.size() - 1) + "/" + projects.size());
        headers.add("Access-Control-Expose-Headers", "Content-Range");
        return new ResponseEntity<>(projects, headers, HttpStatus.OK);
    }
    
    // 상세보기
    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectDetailEntity(@PathVariable("id") int projectId) {
        ProjectEntity project = projectService.getProjectById(projectId);
        if(project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }


    // 4. 프로젝트 수정 (관리자 전용)
    @PutMapping("/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable("id") int projectId,
                                                       @RequestBody ProjectEntity project) {
        project.setProjectId(projectId);
        projectService.updateProject(project);
        // 업데이트 후, 변경된 객체를 다시 조회해서 반환하거나 project 객체를 반환
        ProjectEntity updated = projectService.getProjectById(projectId);
        return ResponseEntity.ok(updated);
    }


    // 5. 프로젝트 삭제 (관리자 전용)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") int projectId) {
        int deletedCount = projectService.deleteProject(projectId);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. 승인/거절 상태 업데이트 (관리자 전용): PUT /admin/project/{id}/status
    @PutMapping("/{id}/status")
    public ResponseEntity<ProjectEntity> updateProjectStatus(@PathVariable("id") int projectId,
                                                             @RequestBody String status) {
        ProjectEntity project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        project.setStatus(status);
        projectService.updateProject(project);
        return ResponseEntity.ok(project);
    }
}
