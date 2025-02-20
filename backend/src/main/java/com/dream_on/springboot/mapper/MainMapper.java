package com.dream_on.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dream_on.springboot.dto.ProjectSummaryDTO;

@Mapper
public interface MainMapper {
    /**
     * 전체 카테고리 상관없이 기부액 기준 인기 TOP15 프로젝트 요약 정보 조회
     */
    List<ProjectSummaryDTO> findTop15ProjectsByDonation();
}
