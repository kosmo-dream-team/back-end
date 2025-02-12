package com.dream_on.springboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dream_on.springboot.dto.PortOnePaymentInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PortOneApiClient {

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    // 1. 액세스 토큰 발급
    public String getAccessToken() {
        // POST 요청: https://api.iamport.kr/users/getToken
        // 요청 데이터: { "imp_key": apiKey, "imp_secret": apiSecret }
        // 응답 예시: { code: 0, message: "", response: { access_token, expired_at, ... } }
        // HTTP Client (RestTemplate, OkHttp 등)로 호출 후 accessToken 리턴
        String accessToken = ""; // 실제 구현 시 HTTP 호출 결과 파싱
        return accessToken;
    }

    // 2. imp_uid로 결제 정보 조회
    public PortOnePaymentInfo getPaymentByImpUid(String impUid) {
        String token = getAccessToken();
        // GET 요청: https://api.iamport.kr/payments/{imp_uid}
        // Header: Authorization: Bearer {token}
        // 응답 예시: { code: 0, message: "성공", response: { imp_uid, status, amount, ... } }
        // JSON 데이터를 파싱하여 PortOnePaymentInfo DTO로 변환 후 리턴
        PortOnePaymentInfo paymentInfo = new PortOnePaymentInfo();
        // 실제 구현: HTTP GET 요청 및 응답 파싱 코드 추가
        return paymentInfo;
    }
}

