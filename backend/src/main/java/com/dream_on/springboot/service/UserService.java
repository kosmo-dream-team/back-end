package com.dream_on.springboot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.MyPageResponseDTO;
import com.dream_on.springboot.dto.UserDTO;
import com.dream_on.springboot.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 로직:
     * 1) 이메일 중복 검사
     * 2) 패스워드 해시 후 DB 저장
     */
    public void registerUser(UserDTO userDTO) {
        // 이메일 중복 체크
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + userDTO.getEmail());
        }
        
        // UserEntity로 변환
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUserName(userDTO.getUser_name());
        newUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash()));
        newUser.setGender(userDTO.getGender());
        newUser.setPhone(userDTO.getPhone());
        newUser.setUserType(userDTO.getUser_type());
        newUser.setBalance(userDTO.getBalance());
        newUser.setIsActive(userDTO.getIs_active() != null ? userDTO.getIs_active() : "Y");
        newUser.setProfileImage(userDTO.getProfile_image());
        // reset_token 등은 null, created_at/updated_at은 DB default or Mapper에서 NOW()
        
        int result = userMapper.insertUser(newUser);
        if(result <= 0) {
            throw new RuntimeException("회원가입 DB Insert 실패");
        }
    }

    /**
     * 로그인 로직:
     * 1) 이메일로 사용자 조회
     * 2) 비밀번호 검증(PasswordEncoder.matches)
     */
    public UserEntity login(String email, String rawPassword) {
        UserEntity user = userMapper.findByEmail(email);
        if (user == null) {
            return null; // 사용자 없음
        }
        // 해시된 비밀번호와 대조
        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return user; // 로그인 성공
        }
        return null; // 비밀번호 불일치 → 로그인 실패
    }

    /**
     * 회원정보 수정
     */
    public void updateUser(UserDTO userDTO) {
        if (userDTO.getUser_id() == 0) {
            throw new IllegalArgumentException("수정할 사용자의 userId가 없습니다.");
        }
        // DB에서 기존 데이터 로드
        UserEntity existing = userMapper.findByUserId(userDTO.getUser_id());
        if(existing == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        // 비밀번호 변경 처리
        if(userDTO.getPassword_hash() != null && !userDTO.getPassword_hash().isEmpty()) {
            existing.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash()));
        }

        // 나머지 필드 업데이트
        existing.setEmail(userDTO.getEmail());
        existing.setUserName(userDTO.getUser_name());
        existing.setGender(userDTO.getGender());
        existing.setPhone(userDTO.getPhone());
        existing.setUserType(userDTO.getUser_type());
        existing.setBalance(userDTO.getBalance());
        existing.setProfileImage(userDTO.getProfile_image());
        // resetToken, resetTokenExpiresAt 등 필요 시 반영

        userMapper.updateUser(existing);
    }

    /**
     * 회원 탈퇴 (is_active = 'N')
     */
    public void deleteUser(int userId) {
        UserEntity existing = userMapper.findByUserId(userId);
        if(existing == null){
            throw new IllegalArgumentException("존재하지 않는 사용자입니다. userId=" + userId);
        }
        int rowCount = userMapper.deleteUser(userId);
        if(rowCount <= 0){
            throw new RuntimeException("회원 탈퇴 실패");
        }
    }

    /**
     * 마이페이지 정보 조회
     * 사용자 기본 정보 + 총 기부액 => DTO 반환
     */
    public MyPageResponseDTO getMyPageInfo(Long userId) {
        MyPageResponseDTO dto = userMapper.findMyPageInfo(userId);
        if (dto == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다. id=" + userId);
        }
        // 총 기부액에 따른 등급 산출 (예: 0~99: Bronze, 100~999: Silver, 1000 이상: Gold)
        dto.setRank(computeRank(dto.getTotalDonation()));
        return dto;
    }

    private String computeRank(int totalDonation) {
        if (totalDonation < 100) {
            return "Bronze";
        } else if (totalDonation < 1000) {
            return "Silver";
        } else {
            return "Gold";
        }
    }
}
