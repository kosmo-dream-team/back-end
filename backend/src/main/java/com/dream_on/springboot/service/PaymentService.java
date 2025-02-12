package com.dream_on.springboot.service;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.PortOnePaymentInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneApiClient portOneApiClient; 

    public void verifyPayment(String impUid) {
        // PortOne API를 호출하여 결제 정보 조회
        PortOnePaymentInfo paymentInfo = portOneApiClient.getPaymentByImpUid(impUid);

        // 결제 상태와 금액 등 검증 (예시: 상태가 paid인지 확인)
        if (!"paid".equals(paymentInfo.getStatus())) {
            throw new IllegalStateException("결제 상태가 paid가 아님");
        }
        // 추가적으로 결제 금액, 주문번호(merchantUid) 등을 검증하여 부정 결제를 방지합니다.

        // 검증 후 DB에 기부 내역 등록 등 추가 로직 수행
        // donationMapper.insert(...);
    }
}
