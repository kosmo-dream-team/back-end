package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * 기부 많이 한 사람 순위 DTO
 * 예) { "user_id": "김진우", "total_donation_count": 30000 }
 */
@Data
public class TopDonorDTO {
    private String user_id;               // 기부자 이름(또는 아이디)
    private long total_donation_count;    // 총 기부액
}
