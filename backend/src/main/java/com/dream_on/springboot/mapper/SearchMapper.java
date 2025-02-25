package com.dream_on.springboot.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dream_on.springboot.dto.ProjectIdDTO;

@Mapper
public interface SearchMapper {
    List<ProjectIdDTO> findProjectsByKeyword(@Param("keyword") String keyword);
}
