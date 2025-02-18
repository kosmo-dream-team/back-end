package com.dream_on.springboot.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	// UserDetailsService를 정의하지 않으면 null 값이 될 수 없다고 에러가 발생해 임시로 만든 파일입니다.
	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity foundUser = userMapper.findByEmail(username);
		
		if(foundUser == null) throw new UsernameNotFoundException("User not found with username: " + username);
		
		return new User(foundUser.getUserName(), foundUser.getPasswordHash(), new ArrayList<>());
	}
}