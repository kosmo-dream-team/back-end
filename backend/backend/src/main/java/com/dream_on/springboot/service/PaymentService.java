package com.dream_on.springboot.service;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.PortOnePaymentInfo;
import lombok.RequiredArgsConstructor;

/**
 * PaymentService
 *
 * <p>PortOne(아임포트) 결제 검증 및 결제 후 DB 처리 로직을 담당합니다.
 * - verifyPayment(): PortOneApiClient를 통해 imp_uid 결제 정보를 조회하고, 상태/금액/주문번호 등을 검증합니다.
 * - 검증 후, DB에 기부 내역 삽입 등 추가 로직을 실행할 수 있습니다.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneApiClient portOneApiClient; 

    /**
     * 결제 검증 로직
     * 
     * @param impUid PortOne 결제 고유 번호(imp_uid)
     * @throws IllegalStateException 결제 상태가 "paid"가 아닌 경우 등 부정 결제일 때 예외
     */
    public void verifyPayment(String impUid) {
        // 1) PortOne API를 호출하여 결제 정보 조회
        PortOnePaymentInfo paymentInfo = portOneApiClient.getPaymentByImpUid(impUid);

        // 2) 결제 상태와 금액, merchantUid 등을 검증
        // 예: 상태가 paid인지 확인
        if (!"paid".equals(paymentInfo.getStatus())) {
            throw new IllegalStateException("결제 상태가 'paid'가 아님 (현재 상태: " + paymentInfo.getStatus() + ")");
        }

        // 예: 결제 금액이 내가 의도한 금액과 일치하는지 확인 (paymentInfo.getAmount() 등)
        // 예: paymentInfo.getMerchantUid()와 내가 생성한 주문번호가 일치하는지 확인

        // 3) 검증 후 DB 처리(예: donation 테이블에 기부 내역 insert)
        // donationMapper.insertDonation(...);
    }
}
