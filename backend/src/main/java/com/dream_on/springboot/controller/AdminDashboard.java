package com.dream_on.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dream_on.springboot.dto.DashboardDTO;
import com.dream_on.springboot.service.DashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminDashboard {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        DashboardDTO dashboard = dashboardService.getDashboardStats();
        return ResponseEntity.ok(dashboard);
    }
}
