package com.dream_on.springboot.dto;

import lombok.Data;

//관리자용 DTO
@Data
public class DonationTrendDTO {
    private String period;       // 예: "2025-03" (월별) 또는 "2025-03-15" (일별)
    private int totalDonation;   // 해당 기간의 총 기부금
}
