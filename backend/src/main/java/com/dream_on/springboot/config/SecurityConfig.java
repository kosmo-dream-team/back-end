package com.dream_on.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // (1) 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/user/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            // (2) OAuth2 로그인 설정
            .oauth2Login(oauth -> oauth
                .loginPage("/user/login")    // 커스텀 로그인 페이지
                .defaultSuccessUrl("/")      // 로그인 성공 시 이동할 경로
            )
            // (3) 폼 로그인(기존 로컬 로그인 사용 시)
            .formLogin(form -> form
                .loginPage("/user/login")    // 로그인 페이지
                .permitAll()
            )

            // (4) 로그인 상태 유지 (UI 'login' remember-me checkbox)            
            .rememberMe(r -> r
                    .rememberMeParameter("remember-me") // 폼에서 체크박스 name 속성
                    .tokenValiditySeconds(14 * 24 * 60 * 60) // 2주
                    .key("rememberMeKey") // 서명용 key
                )
            
            // (5) 로그아웃
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
            )
            // (6) CSRF
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
