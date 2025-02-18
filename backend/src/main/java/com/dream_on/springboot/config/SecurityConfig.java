package com.dream_on.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dream_on.springboot.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// UserDetailsService를 정의하지 않으면 null 값이 될 수 없다고 에러가 발생해 임시로 만든 파일입니다.
	private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
	        // CORS 설정을 활성화
	        .cors()
	        .and()
            // (1) 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/user/**", "/css/**", "/js/**", "/project/**").permitAll()
                .anyRequest().authenticated()
            )
            // (2) OAuth2 로그인 설정
            .oauth2Login(oauth -> oauth
                .loginPage("/user/login")    // 커스텀 로그인 페이지
                .defaultSuccessUrl("/")      // 로그인 성공 시 이동할 경로
            )
            // (3) 폼 로그인(기존 로컬 로그인 사용 시)
//            .formLogin(form -> form
//                .loginPage("/user/login")    // 로그인 페이지
//                .permitAll()
//            )
            .formLogin().disable()
            // (4) 로그인 상태 유지 (UI 'login' remember-me checkbox)            
            .rememberMe(r -> r
                .rememberMeParameter("remember-me") // 폼에서 체크박스 name 속성
                .tokenValiditySeconds(14 * 24 * 60 * 60) // 2주
                .key("rememberMeKey") // 서명용 key
//                .userDetailsService(customUserDetailsService)
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
    
    // CORS 설정을 위한 Bean 추가
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // React 앱의 URL (예시: http://localhost:3000)
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*");  // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격증명 허용

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 설정 적용

        return source;
    }
}
