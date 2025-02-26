package com.dream_on.springboot.mapper;

import com.dream_on.springboot.domain.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CategoryMapper {
	@Select("SELECT category_id AS categoryId, category AS categoryName FROM category ORDER BY category_id ASC")
	List<CategoryEntity> findAllCategories();

}
