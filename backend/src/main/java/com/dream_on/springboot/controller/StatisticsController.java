package com.dream_on.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream_on.springboot.dto.StatisticsResponseDTO;
import com.dream_on.springboot.service.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    // GET /api/state
    @GetMapping("/state")
    public ResponseEntity<StatisticsResponseDTO> getState() {

    	// 컨트롤러가 정상적으로 도달했는지 확인
        System.out.println("===== [DEBUG] /api/state 컨트롤러 진입 =====");    	

        StatisticsResponseDTO result = statisticsService.getStatistics();

        // result 값 확인
        System.out.println("===== [DEBUG] /api/state result: " + result);    	
    	
    	return ResponseEntity.ok(result);
    }
}


/*
@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    // GET /api/state
    @GetMapping("/api/state")
    public ResponseEntity<StatisticsResponseDTO> getState() {

    	// 컨트롤러가 정상적으로 도달했는지 확인
        System.out.println("===== [DEBUG] /api/state 컨트롤러 진입 =====");    	

        StatisticsResponseDTO result = statisticsService.getStatistics();

        // result 값 확인
        System.out.println("===== [DEBUG] /api/state result: " + result);    	
    	
    	return ResponseEntity.ok(result);
    }
}
*/