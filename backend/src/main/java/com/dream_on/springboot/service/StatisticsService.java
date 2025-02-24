package com.dream_on.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.MonthlyDonationDTO;
import com.dream_on.springboot.dto.StatisticsResponseDTO;
import com.dream_on.springboot.dto.TopDonorDTO;
import com.dream_on.springboot.mapper.StatisticsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsMapper statisticsMapper;

    public StatisticsResponseDTO getStatistics() {
        StatisticsResponseDTO dto = new StatisticsResponseDTO();

        // 1) 총 기부금
        long totalDonationAmount = statisticsMapper.getTotalDonationAmount();
        dto.setTotalDonationAmount(totalDonationAmount);

        // 2) 총 캠페인 수
        int totalCampaignCount = statisticsMapper.getTotalCampaignCount();
        dto.setTotalCampaignCount(totalCampaignCount);

        // 3) 총 기부횟수
        int totalDonationCount = statisticsMapper.getTotalDonationCount();
        dto.setTotalDonationCount(totalDonationCount);

        // 4) 최근 12개월 월별 기부금
        List<MonthlyDonationDTO> monthlyChange = statisticsMapper.getMonthlyDonationChange();
        dto.setMonthlyDonationChange(monthlyChange);

        // 5) 기부 많이 한 사람 순위
        List<TopDonorDTO> topDonors = statisticsMapper.getTopDonorRanking();
        dto.setTopDonorRanking(topDonors);

        // 6) 1인당 평균 기부금 (예: 총 기부금 / 전체 유저수)
        int totalUserCount = statisticsMapper.getTotalUserCount();
        // 혹은 실제 기부 참여 유저만 세는 쿼리를 별도로 만들 수도 있음
        long averageDonation = 0;
        if (totalUserCount > 0) {
            averageDonation = totalDonationAmount / totalUserCount;
        }
        dto.setAverageDonation(averageDonation);

        return dto;
    }
}
