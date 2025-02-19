package com.dream_on.springboot.controller;

import com.dream_on.springboot.domain.CategoryEntity;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 카테고리 페이지 관련 컨트롤러
 *
 * 1) "카테고리" 클릭 시: 전체 카테고리 목록 + 전체 기부금액 10위까지 프로젝트
 * 2) "개별 카테고리" 클릭 시: 특정 카테고리에 대한 기부액 상위 10위 프로젝트
 */
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 1) "카테고리" 클릭 시
     *    - 전체 카테고리 목록
     *    - 전체 기부액 상위 10위 프로젝트 정보
     * Input : 없음
     * Output : { 
     *   "categories": [...CategoryEntity...],
     *   "topProjects": [...ProjectSummaryDTO...]
     * }
     */
    @GetMapping
    public ResponseEntity<AllCategoryResponse> getAllCategoriesAndTopProjects() {
        // 전체 카테고리 목록
        List<CategoryEntity> categories = categoryService.getAllCategories();
        // 전체 카테고리 대상 기부액 상위 10위 프로젝트
        List<ProjectSummaryDTO> topProjects = categoryService.getTop10ProjectsAllCategory();

        // 응답용 DTO or VO를 구성
        AllCategoryResponse response = new AllCategoryResponse();
        response.setCategories(categories);
        response.setTopProjects(topProjects);

        return ResponseEntity.ok(response);
    }

    /**
     * 2) "개별 카테고리" 클릭 시
     *    - 해당 카테고리 내 기부금액 10위까지 프로젝트 정보
     * Input : categoryId (PathVariable or QueryParam)
     * Output : List<ProjectSummaryDTO>
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProjectSummaryDTO>> getTop10ProjectsInCategory(
            @PathVariable("categoryId") int categoryId) {

        List<ProjectSummaryDTO> projects = categoryService.getTop10ProjectsInCategory(categoryId);
        return ResponseEntity.ok(projects);
    }

    /**
     * AllCategoryResponse
     * <p>
     * 이너 클래스 형태로 간단히 작성해, 
     * 카테고리 목록 + 상위 10개 프로젝트를 함께 전달하기 위한 DTO
     */
    public static class AllCategoryResponse {
        private List<CategoryEntity> categories;
        private List<ProjectSummaryDTO> topProjects;

        public List<CategoryEntity> getCategories() {
            return categories;
        }
        public void setCategories(List<CategoryEntity> categories) {
            this.categories = categories;
        }
        public List<ProjectSummaryDTO> getTopProjects() {
            return topProjects;
        }
        public void setTopProjects(List<ProjectSummaryDTO> topProjects) {
            this.topProjects = topProjects;
        }
    }
}
