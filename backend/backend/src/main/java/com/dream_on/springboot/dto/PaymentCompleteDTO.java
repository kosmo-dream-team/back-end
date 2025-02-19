package com.dream_on.springboot.dto;

import lombok.Data;

/**
 * PaymentCompleteDTO
 *
 * <p>PortOne(아임포트) 결제 완료 후, 프론트엔드에서 백엔드로 전달할 결제 관련 정보를 담는 DTO입니다.
 *  - impUid: PortOne에서 발급한 결제 고유번호 (imp_uid)
 *  - merchantUid: 주문 번호 (백엔드에서 생성한 고유 주문번호)
 * </p>
 */
@Data
public class PaymentCompleteDTO {
    private String impUid;
    private String merchantUid;
}
