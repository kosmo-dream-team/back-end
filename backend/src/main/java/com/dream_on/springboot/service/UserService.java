package com.dream_on.springboot.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.LoginRequestDTO;
import com.dream_on.springboot.dto.LoginResponseDTO;
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
     * 회원정보 수정
     */
    public void updateUser(UserDTO userDTO) {
        if (userDTO.getUser_id() == 0) {
            throw new IllegalArgumentException("수정할 사용자의 userId가 없습니다.");
        } else {
            UserEntity existing = this.userMapper.findByUserId(userDTO.getUser_id());
            if (existing == null) {
                throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
            } else {
                if (userDTO.getPassword_hash() != null && !userDTO.getPassword_hash().isEmpty()) {
                    existing.setPasswordHash(this.passwordEncoder.encode(userDTO.getPassword_hash()));
                }

                existing.setEmail(userDTO.getEmail());
                existing.setUserName(userDTO.getUser_name());
                existing.setGender(userDTO.getGender());
                existing.setPhone(userDTO.getPhone());
                existing.setUserType(userDTO.getUser_type());
                existing.setBalance(userDTO.getBalance());
                if (userDTO.getProfile_image() != null && !userDTO.getProfile_image().isEmpty()) {
                    existing.setProfileImage(userDTO.getProfile_image());
                }

                int updated = this.userMapper.updateUser(existing);
                if (updated <= 0) {
                    throw new RuntimeException("회원정보 수정 실패: DB update 영향받은 행이 없습니다.");
                }
            }
        }
    }
    
    
    /**
     * 회원가입 로직:
     * 1) 이메일 중복 검사
     * 2) 패스워드 암호화 후 DB 저장
     */
    public void registerUser(UserDTO userDTO) {
        // 이메일 중복 체크: 이미 가입된 이메일이면 예외 발생
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + userDTO.getEmail());
        }
        
        // UserDTO를 UserEntity로 변환
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUserName(userDTO.getUser_name());
        newUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash())); // 비밀번호 암호화 적용
        newUser.setGender(userDTO.getGender());
        newUser.setPhone(userDTO.getPhone());
        newUser.setUserType(userDTO.getUser_type());
        // 회원가입 시에는 보통 신규 사용자는 0의 잔액을 가지며, 활성 상태("Y")로 설정
        newUser.setBalance(0);
        newUser.setIsActive("Y");
        // 프로필 이미지는 요청서에 없으므로 기본값(null) 설정 (필요시 default 이미지 경로 설정 가능)
        newUser.setProfileImage(null);
        // reset_token, created_at, updated_at 등은 DB 기본값이나 Mapper에서 처리하도록 설정

        // 회원 등록: insertUser 메서드를 호출하여 DB에 등록
        int result = userMapper.insertUser(newUser);
        if (result <= 0) {
            throw new RuntimeException("회원가입 DB Insert 실패");
        }
    }

    /**
     * 프론트 요청(LoginRequestDTO) 기반 로그인 처리
     * - password_hash 필드를 "raw password"로 간주
     * - DB의 passwordHash와 비교
     * - 일치 시 LoginResponseDTO 생성
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1) 이메일로 사용자 조회
        UserEntity user = userMapper.findByEmail(request.getEmail());
        if (user == null) {
            return null; // 사용자 없음
        }

        // 2) 비밀번호 비교
        // 프론트에서 "password_hash"를 실제 '비밀번호'로 보낸다고 가정
        // DB에 저장된 user.getPasswordHash()는 해시된 값
        boolean matches = passwordEncoder.matches(
            request.getPassword_hash(), 
            user.getPasswordHash()
        );
        if (!matches) {
            return null; // 비밀번호 불일치
        }

        // 3) 총 기부액 조회
        int totalDonation = userMapper.sumDonationAmount(user.getUserId().intValue());

        // 4) 등급 계산
        String rank = computeRank(totalDonation);

        // 5) LoginResponseDTO 생성
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUser_id(user.getUserId());
        responseDTO.setUser_name(user.getUserName()); 
        responseDTO.setPassword_hash(user.getPasswordHash()); // 요청서에 "비번번호"로 응답 요구
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setGender(user.getGender());
        responseDTO.setUser_type(user.getUserType());
        responseDTO.setRank(rank);
        responseDTO.setTotal_donation_count(totalDonation);
        responseDTO.setProfile_image(user.getProfileImage());

        return responseDTO;
    }    
    

    
    // 예시: 기부액에 따른 등급 계산
    private String computeRank(int totalDonation) {
        if (totalDonation < 100000) {
            return "브론즈";
        } else if (totalDonation < 1000000) {
            return "실버";
        } else {
            return "골드";
        }
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

//    private String computeRank(int totalDonation) {
//        if (totalDonation < 100000) {
//            return "Bronze";
//        } else if (totalDonation < 1000000) {
//            return "Silver";
//        } else {
//            return "Gold";
//        }
//    }
//
//    /**
//     * 로그인 로직:
//     * 1) 이메일로 사용자 조회
//     * 2) 비밀번호 검증(PasswordEncoder.matches)
//     */
//    public UserEntity login(String email, String rawPassword) {
//        UserEntity user = userMapper.findByEmail(email);
//        if (user == null) {
//            return null; // 사용자 없음
//        }
//        // 해시된 비밀번호와 대조
//        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
//            return user; // 로그인 성공
//        }
//        return null; // 비밀번호 불일치 → 로그인 실패
//    }


    /**
     * 회원가입 로직:
     * 1) 이메일 중복 검사
     * 2) 패스워드 해시 후 DB 저장

    public void registerUser(UserDTO userDTO) {
        // 이메일 중복 체크: 이미 가입된 이메일이면 예외 발생
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + userDTO.getEmail());
        }
        
        // UserDTO를 UserEntity로 변환
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUserName(userDTO.getUser_name());
        newUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword_hash())); // 비밀번호 암호화 적용
        newUser.setGender(userDTO.getGender());
        newUser.setPhone(userDTO.getPhone());
        newUser.setUserType(userDTO.getUser_type());
        // newUser.setBalance(userDTO.getBalance());
        // is_active가 null이면 "Y"로 설정 (기본값)
        // newUser.setIsActive(userDTO.getIs_active() != null ? userDTO.getIs_active() : "Y");
        newUser.setProfileImage(userDTO.getProfile_image());
        // reset_token, created_at, updated_at 등은 DB 기본값이나 Mapper에서 처리하도록 설정

        // 회원 등록: insertUser 메서드를 호출하여 DB에 등록
        int result = userMapper.insertUser(newUser);
        if(result <= 0) {
            throw new RuntimeException("회원가입 DB Insert 실패");
        }
    }
     */
    

    /**
     * 회원정보 수정

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
     */
    
    public List<UserEntity> getAllUsers() {
        return userMapper.findAll();
    }
    
    public UserEntity getUserById(int userId) {
        return userMapper.findByUserId(userId);
    }
  
}
