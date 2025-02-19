package com.dream_on.springboot.service;

import com.dream_on.springboot.domain.CategoryEntity;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.mapper.CategoryMapper;
import com.dream_on.springboot.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 카테고리 관련 서비스
 *
 * 1) 전체 카테고리 목록 조회
 * 2) 전체 카테고리 대상 기부액 상위 10위 프로젝트 조회
 * 3) 특정 카테고리 대상 기부액 상위 10위 프로젝트 조회
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final ProjectMapper projectMapper;

    /**
     * 전체 카테고리 목록을 반환
     */
    public List<CategoryEntity> getAllCategories() {
        return categoryMapper.findAllCategories();
    }

    /**
     * (전체 카테고리) 기부액 상위 10위 프로젝트 목록
     */
    public List<ProjectSummaryDTO> getTop10ProjectsAllCategory() {
        return projectMapper.findTop10ProjectsByDonation();
    }

    /**
     * (개별 카테고리) 기부액 상위 10위 프로젝트 목록
     * @param categoryId 조회할 카테고리 ID
     */
    public List<ProjectSummaryDTO> getTop10ProjectsInCategory(int categoryId) {
        return projectMapper.findTop10ProjectsByDonationInCategory(categoryId);
    }
}
