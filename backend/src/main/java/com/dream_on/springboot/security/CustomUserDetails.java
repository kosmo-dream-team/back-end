package com.dream_on.springboot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String name;       // 사용자 이름
    private String grade;      // 사용자 등급 (예: VIP, 일반 등)
    private String profileUrl; // 프로필 이미지 URL 또는 기타 프로필 정보
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String name, String grade, String profileUrl, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.grade = grade;
        this.profileUrl = profileUrl;
        this.authorities = authorities;
    }

    // 추가 정보에 대한 getter 메서드
    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    // UserDetails 인터페이스 구현 메서드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
