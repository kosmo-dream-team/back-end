package com.dream_on.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.dto.PaymentCompleteDTO;
import com.dream_on.springboot.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 완료 콜백
     * 
     * <p>
     * 프론트엔드에서 IMP.request_pay()가 성공하면 imp_uid, merchant_uid 등을 PaymentCompleteDTO로 전송합니다.
     * 이 메서드는 PortOne REST API를 통해 해당 imp_uid 결제 정보를 조회하고 검증한 뒤,
     * DB에 결제 내역(기부 내역 등)을 저장하는 흐름을 제어합니다.
     * </p>
     *
     * @param dto PaymentCompleteDTO (impUid, merchantUid)
     * @return 결제 검증 결과 메시지
     */
    @PostMapping("/complete")
    public ResponseEntity<String> paymentComplete(@RequestBody PaymentCompleteDTO dto) {
        try {
            String impUid = dto.getImpUid();
            String merchantUid = dto.getMerchantUid();

            // 1) PortOne REST API를 통해 결제 내역 검증
            paymentService.verifyPayment(impUid);

            // 2) DB 처리 (예: 기부 내역 저장 등)
            // paymentService.saveDonation(merchantUid, ...);

            return ResponseEntity.ok("결제 검증 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("결제 검증 실패: " + e.getMessage());
        }
    }
}
