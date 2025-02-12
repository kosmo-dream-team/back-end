package com.dream_on.springboot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.UserDTO;
import com.dream_on.springboot.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * 회원가입 로직:
     * 1) 비밀번호/비번확인 일치 여부
     * 2) 이메일 중복 검사
     * 3) 패스워드 해시 후 DB 저장
     */
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO) {

        // 2) 이메일 중복 체크
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + userDTO.getEmail());
        }

        // 3) UserEntity로 변환
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUser_name());
        newUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash()));

        // A창(직전)에서 결정된 user_type
        newUser.setUserType(userDTO.getUser_type());

        // rank, accumulatedDonation, isActive, profileImage 등
        newUser.setRank(userDTO.getRank() != null ? userDTO.getRank() : "브론즈");
        newUser.setIsActive(userDTO.getIs_active() != null ? userDTO.getIs_active() : "Y");
        newUser.setProfileImage(userDTO.getProfile_image());

        // DB insert
        int result = userMapper.insertUser(newUser);
        if(result <= 0) {
            throw new RuntimeException("회원가입 DB Insert 실패");
        }
        // created_at, updated_at은 DB default or trigger로 자동 설정 가정
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

        // 비밀번호를 바꾸지 않으려면 password가 비어있는지 확인
        // 만약 password가 빈 값이 아니라면 => 비밀번호 변경 로직
        // passwordConfirm 검증 여부는 선택사항(비밀번호 변경 때만 유효)
        
        if(userDTO.getPassword_hash() != null && !userDTO.getPassword_hash().isEmpty()) {
            // 비밀번호 변경 모드라면
            // passwordConfirm 로직 등 검증 후
            existing.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash()));
        }

        // email, username, profileImage 등은 항상 업데이트 가능
        existing.setEmail(userDTO.getEmail());
        existing.setUsername(userDTO.getUser_name());
        existing.setProfileImage(userDTO.getProfile_image());
        // rank, userType 등도 필요시 수정
        
        userMapper.updateUser(existing);
    }

    

    /**
     * 회원 탈퇴 (is_active = 'N')
     */
    public void deleteUser(int userId) {
        // 1) DB 조회(존재 여부 확인)
        UserEntity existing = userMapper.findByUserId(userId);
        if(existing == null){
            throw new IllegalArgumentException("존재하지 않는 사용자입니다. userId=" + userId);
        }

        // 2) update is_active='N'
        int rowCount = userMapper.deleteUser(userId);
        if(rowCount <= 0){
            throw new RuntimeException("회원 탈퇴 실패");
        }
    }    
    
}
