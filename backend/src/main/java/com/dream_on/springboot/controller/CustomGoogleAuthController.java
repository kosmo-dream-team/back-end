package com.dream_on.springboot.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomGoogleAuthController implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        String jsonUserData = "";
        if (authentication instanceof OAuth2AuthenticationToken) {
            Map<String, Object> userAttributes = ((OAuth2AuthenticationToken) authentication)
                                                    .getPrincipal().getAttributes();
            System.out.println("Google User Attributes: " + userAttributes);
            // Map을 JSON 문자열로 변환
            jsonUserData = objectMapper.writeValueAsString(userAttributes);
        }
        // 프론트엔드 회원가입 페이지로 리다이렉트하면서, 구글 사용자 정보를 쿼리 파라미터로 전달
        String redirectUrl = "http://localhost:5173/signup?googleUser=" 
                             + URLEncoder.encode(jsonUserData, StandardCharsets.UTF_8.toString());
        response.sendRedirect(redirectUrl);
    }
}
