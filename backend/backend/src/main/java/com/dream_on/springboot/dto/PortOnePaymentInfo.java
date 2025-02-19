package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * PortOnePaymentInfo
 *
 * <p>PortOne(아임포트) 결제 정보를 담는 DTO.</p>
 */
@Data
public class PortOnePaymentInfo {

    private String impUid;       // 결제 고유번호 (imp_uid)
    private String merchantUid;  // 주문번호 (merchant_uid)
    private String status;       // 결제 상태 (paid, cancelled 등)
    private int amount;          // 결제 금액

    // 필요하면 결제일시, 카드사, PG사 등 다른 필드를 추가할 수 있음
}
