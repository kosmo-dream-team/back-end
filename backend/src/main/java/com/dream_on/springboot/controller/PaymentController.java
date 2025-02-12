package com.dream_on.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream_on.springboot.dto.PaymentCompleteDTO;
import com.dream_on.springboot.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<String> paymentComplete(@RequestBody PaymentCompleteDTO dto) {
        try {
            String impUid = dto.getImpUid();
            String merchantUid = dto.getMerchantUid();

            // PortOne REST API를 통해 결제 내역 검증
            paymentService.verifyPayment(impUid);

            // 결제 내역에 따른 DB 처리 (예: 기부 내역 저장 등)
            // paymentService.saveDonation(merchantUid, ...);

            return ResponseEntity.ok("결제 검증 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("결제 검증 실패: " + e.getMessage());
        }
    }
}

