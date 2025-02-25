package com.dream_on.springboot.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dream_on.springboot.dto.ProjectIdDTO;
import com.dream_on.springboot.service.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    
    // GET /api/search?q=검색어
    @GetMapping("/search")
    public ResponseEntity<List<ProjectIdDTO>> searchProjects(@RequestParam("keyword") String keyword) {
        System.out.println("===== [DEBUG] /api/search 컨트롤러 진입, keyword: " + keyword);
        List<ProjectIdDTO> results = searchService.getSearchResults(keyword);
        System.out.println("===== [DEBUG] 검색 결과: " + results);
        return ResponseEntity.ok(results);
    }
}
