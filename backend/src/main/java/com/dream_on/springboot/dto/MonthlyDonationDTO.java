package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 월별 기부금 DTO
 * 예) { "year": "2025-02", "donation": 30000 }
 */
@Data
public class MonthlyDonationDTO {
    private String year;      // "YYYY-MM"
    private long donation;    // 해당 월 기부금 합계
}
