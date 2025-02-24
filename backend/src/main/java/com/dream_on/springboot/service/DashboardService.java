package com.dream_on.springboot.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.dream_on.springboot.dto.DashboardDTO;
import com.dream_on.springboot.dto.DonationTrendDTO;
import com.dream_on.springboot.dto.DonationTopDonorDTO;
import com.dream_on.springboot.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardDTO getDashboardStats() {
        int totalUsers = dashboardMapper.countUsers();
        int totalProjects = dashboardMapper.countProjects();
        int totalDonations = dashboardMapper.sumDonations();
        double avgDonation = totalUsers > 0 ? (double) totalDonations / totalUsers : 0.0;
        List<DonationTrendDTO> trends = dashboardMapper.getMonthlyDonationTrends();
        List<DonationTopDonorDTO> topDonors = dashboardMapper.getTopDonors();

        DashboardDTO dto = new DashboardDTO();
        dto.setTotalUsers(totalUsers);
        dto.setTotalProjects(totalProjects);
        dto.setTotalDonations(totalDonations);
        dto.setAvgDonationPerUser(avgDonation);
        dto.setDonationTrends(trends);
        dto.setTopDonors(topDonors);
        return dto;
    }
}
