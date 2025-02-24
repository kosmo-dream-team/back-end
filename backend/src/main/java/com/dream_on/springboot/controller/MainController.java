package com.dream_on.springboot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.StatisticsResponseDTO;
import com.dream_on.springboot.service.MainService;
import com.dream_on.springboot.service.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final StatisticsService statisticsService;
    
    /**
     * 인기 TOP15 프로젝트 정보를 반환하는 API
     * GET /api/topproject
     */
    @GetMapping("/topproject")
    public ResponseEntity<List<ProjectSummaryDTO>> getTopProjects() {

    	// 컨트롤러가 정상적으로 도달했는지 확인
        System.out.println("===== [DEBUG] /api/topproject 컨트롤러 진입 =====");    	
    	
    	List<ProjectSummaryDTO> projects = mainService.getTop15Projects();

        // result 값 확인
        System.out.println("===== [DEBUG] /api/state result: " + projects);    	
    	    	
    	return ResponseEntity.ok(projects);
    }

    
    
    /**
     * 인기 TOP15 프로젝트와 통계 정보를 함께 반환하는 API
     * GET /api/topproject

    @GetMapping("/topproject")
    public ResponseEntity<Map<String, Object>> getTopProjects() {
        // 디버그 로그: 컨트롤러 진입 확인
        System.out.println("===== [DEBUG] /api/topproject 컨트롤러 진입 =====");
        
        // 인기 TOP15 프로젝트 조회
        List<ProjectSummaryDTO> projects = mainService.getTop15Projects();
        System.out.println("===== [DEBUG] Top Projects: " + projects);
        
        // 통계 정보 조회
        StatisticsResponseDTO stats = statisticsService.getStatistics();
        System.out.println("===== [DEBUG] Statistics: " + stats);
        
        // 두 데이터를 Map에 담아서 반환
        Map<String, Object> result = new HashMap<>();
        result.put("topProjects", projects);
        result.put("statistics", stats);
        
        return ResponseEntity.ok(result);
    }
     */

    
    /**
     * 인기 TOP15 프로젝트 정보를 반환하는 API
     * GET /api/topproject

    @GetMapping("/topproject")
    public ResponseEntity<StatisticsResponseDTO> getTopProjects() {

    	// 컨트롤러가 정상적으로 도달했는지 확인
        System.out.println("===== [DEBUG] /api/state 컨트롤러 진입 =====");    	

        StatisticsResponseDTO result = statisticsService.getStatistics();

        // result 값 확인
        System.out.println("===== [DEBUG] /api/state result: " + result);    	
    	
    	return ResponseEntity.ok(result);

    
    }
     */

    


    // 통계 정보 엔드포인트 추가
    /*
    @GetMapping("/state")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        System.out.println("MainController: getStatistics() 호출됨");
        StatisticsResponseDTO stats = statisticsService.getStatistics();
        return ResponseEntity.ok(stats);
    }
	*/
}



/*
 package com.dream_on.springboot.controller;
 

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream_on.springboot.dto.LoginInfoDTO;
import com.dream_on.springboot.dto.MainPageResponse;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.security.CustomUserDetails;
import com.dream_on.springboot.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<MainPageResponse> getMainPageInfo(Authentication authentication) {
        MainPageResponse response = new MainPageResponse();

        // 로그인 정보 전달: 사용자가 인증되었다면 loginInfo를 채워서 전달
        if (authentication != null && authentication.isAuthenticated()) {
            // 여기서는 CustomUserDetails 클래스로 캐스팅하는 예시입니다.
            // CustomUserDetails는 사용자 이름, 등급, 프로필 URL 등의 정보를 포함한다고 가정합니다.
            Object principal = authentication.getPrincipal();
            if(principal instanceof CustomUserDetails) {
                CustomUserDetails user = (CustomUserDetails) principal;
                LoginInfoDTO loginInfo = new LoginInfoDTO();
                loginInfo.setName(user.getName());
                loginInfo.setGrade(user.getGrade());
                loginInfo.setProfile(user.getProfileUrl());
                response.setLoginInfo(loginInfo);
            }
        }
        // 기부 상위 10개 프로젝트 정보 조회 (ProjectSummaryDTO에는 프로젝트명, 카테고리, 목표일, 남은 일수, 목표금액, 도달율, 프로젝트 이미지 등의 필드가 포함되어 있음)
        List<ProjectSummaryDTO> projects = projectService.getTop10Projects();
        response.setTopProjects(projects);

        // 최근 신설된 프로젝트 3개 정보
        List<RecentProjectDTO> recentProjects = projectService.getLatest3Projects();
        response.setRecentProjects(recentProjects);
        
        
        return ResponseEntity.ok(response);
    }
}
*/