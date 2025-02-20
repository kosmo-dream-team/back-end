package com.dream_on.springboot.mapper;

import com.dream_on.springboot.domain.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 카테고리 관련 쿼리를 정의하는 인터페이스.
 */
@Mapper
public interface CategoryMapper {

    /**
     * 전체 카테고리 목록 조회
     * @return 모든 카테고리 목록
     */
    @Select("SELECT category_id, category FROM category ORDER BY category_id ASC")
    List<CategoryEntity> findAllCategories();
}
