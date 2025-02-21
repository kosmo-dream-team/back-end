package com.dream_on.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dream_on.springboot.controller.CustomGoogleAuthController;
import com.dream_on.springboot.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // (1) CORS 활성화 + CSRF 비활성화
            .cors().and()
            .csrf().disable()
            
            // (2) 인증/인가 정책
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/login",
                    "/api/signup",
                    "/api/update/userProfile",
                    "/api/topproject",
                    "/project/**", 
                    "/comment/**",
                    "/css/**",
                    "/js/**",
                    "/oauth2/**",           // OAuth2 시작 엔드포인트 허용
                    "/login/oauth2/**"      // OAuth2 리디렉션(콜백) URI 허용
                ).permitAll()
                .anyRequest().authenticated()
            )
            
            // (3) OAuth2 로그인 설정
            .oauth2Login(oauth -> oauth
                // authorizationEndpoint: OAuth2 인증을 시작하는 엔드포인트의 기본 URI 설정
                .authorizationEndpoint(authorization -> authorization
                    .baseUri("/oauth2/authorization")
                )
                // redirectionEndpoint: OAuth2 인증 후 콜백 요청을 받는 URI 설정
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/*")
                )
                // 커스텀 로그인 페이지 (실제 존재하는 페이지여야 함)
                .loginPage("/login")
                // 로그인 성공 후 이동할 기본 URL : controller 패키지 -> CustomGoogleAuthController에 
                //												 localhost5173:/ 을 매핑해두었습니다.
                .successHandler(new CustomGoogleAuthController())
            )
            
            // (4) 폼 로그인 비활성화 (REST API 전용일 경우)
            .formLogin().disable()
            
            
            
            // (5) Remember-me 설정 (선택 사항)
            .rememberMe(r -> r
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(14 * 24 * 60 * 60) // 2주
                .key("rememberMeKey")
                .userDetailsService(customUserDetailsService)
            )
            
            // (6) 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
            );

        return http.build();
    }

    /**
     * CORS 설정 Bean
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 허용할 Origin (React 개발 서버 예시)
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // 쿠키 등 인증정보 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
