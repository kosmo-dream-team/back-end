package com.dream_on.springboot.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.dream_on.springboot.dto.ProjectIdDTO;
import com.dream_on.springboot.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchMapper searchMapper;
    
    public List<ProjectIdDTO> getSearchResults(String keyword) {
        String searchPattern = "%" + keyword.trim() + "%";
        return searchMapper.findProjectsByKeyword(searchPattern);
    }
}
