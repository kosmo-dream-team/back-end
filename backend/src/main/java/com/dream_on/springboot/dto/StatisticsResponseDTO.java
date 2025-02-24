package com.dream_on.springboot.dto;

import java.util.List;

import lombok.Data;

/**
 * 통계 응답 DTO
 * 
 * GET /api/state
 */
@Data
public class StatisticsResponseDTO {

    // 총 기부금
    private long totalDonationAmount;

    // 총 캠페인 수
    private int totalCampaignCount;

    // 총 기부횟수
    private int totalDonationCount;

    // 월별 기부금 추이 (최근 12개월)
    private List<MonthlyDonationDTO> monthlyDonationChange;

    // 기부 많이 한 사람 순위
    private List<TopDonorDTO> topDonorRanking;

    // 1인당 평균 기부금
    private long averageDonation;
}
