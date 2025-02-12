package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class PortOnePaymentInfo {
    private String imp_uid;
    private String merchant_uid;
    private String status;  // 예: paid, cancelled 등
    private int amount;     // 결제 금액
    // 기타 필요한 필드 추가
}
