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
                // 로그인, 회원가입 등은 인증 없이 허용
                .requestMatchers(
                	"/api/login",
                    "/api/signup",
                    "/api/update/userProfile",   
                    "/api/topproject",                    
                    "/css/**", 
                    "/js/**"
                ).permitAll()
                // 나머지 요청은 인증 필요
                .anyRequest().authenticated()
            )

            // (3) OAuth2 로그인 (필요 시)
            .oauth2Login(oauth -> oauth
                // 커스텀 로그인 페이지 경로
                .loginPage("/api/login")
                // 로그인 성공 후 이동할 기본 URL
                .defaultSuccessUrl("/")
            )

            // (4) 폼 로그인 비활성화 (REST API만 사용 시)
            .formLogin().disable()

            // (5) Remember-me 설정 (선택)
            .rememberMe(r -> r
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(14 * 24 * 60 * 60) // 2주
                .key("rememberMeKey")
                // 커스텀 UserDetailsService 연결
                .userDetailsService(customUserDetailsService)
            )

            // (6) 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
            );

        // 최종 FilterChain 빌드
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
        configuration.setAllowCredentials(true); // 인증정보(쿠키 등) 허용 여부

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 해당 CORS 정책 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
