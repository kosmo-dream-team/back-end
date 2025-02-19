package com.dream_on.springboot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dream_on.springboot.dto.PortOnePaymentInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PortOneApiClient {

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 액세스 토큰 발급
     * 
     * POST 요청을 통해 PortOne의 액세스 토큰을 발급받습니다.
     * 요청 URL: https://api.iamport.kr/users/getToken
     * 요청 데이터: { "imp_key": apiKey, "imp_secret": apiSecret }
     * 응답 예시: { code: 0, message: "", response: { access_token, expired_at, ... } }
     * 
     * @return 액세스 토큰 문자열
     */
    public String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imp_key", apiKey);
        requestBody.put("imp_secret", apiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");
            return (String) responseBody.get("access_token");
        }
        throw new RuntimeException("PortOne 토큰 발급 실패: " + response.getStatusCode());
    }

    /**
     * imp_uid를 이용한 결제 정보 조회
     * 
     * GET 요청을 통해 PortOne 서버에서 imp_uid에 해당하는 결제 정보를 조회합니다.
     * 요청 URL: https://api.iamport.kr/payments/{imp_uid}
     * Header: Authorization: Bearer {access_token}
     * 
     * @param impUid 결제 고유 번호
     * @return PortOnePaymentInfo DTO에 파싱된 결제 정보
     */
    public PortOnePaymentInfo getPaymentByImpUid(String impUid) {
        String accessToken = getAccessToken();
        String url = "https://api.iamport.kr/payments/" + impUid;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");
            // PortOnePaymentInfo DTO에 필요한 필드에 responseBody 값을 매핑합니다.
            PortOnePaymentInfo paymentInfo = new PortOnePaymentInfo();
            paymentInfo.setImpUid((String) responseBody.get("imp_uid"));
            paymentInfo.setMerchantUid((String) responseBody.get("merchant_uid"));
            paymentInfo.setStatus((String) responseBody.get("status"));
            paymentInfo.setAmount(((Number) responseBody.get("amount")).intValue());
            // 필요에 따라 추가 필드를 매핑합니다.
            return paymentInfo;
        }
        throw new RuntimeException("결제 정보 조회 실패: " + response.getStatusCode());
    }
}
