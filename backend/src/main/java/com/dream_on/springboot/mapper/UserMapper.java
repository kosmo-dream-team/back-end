package com.dream_on.springboot.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.MyPageResponseDTO;

@Mapper
public interface UserMapper {

    // 이메일로 사용자 조회
    UserEntity findByEmail(@Param("email") String email);

    // user_id로 사용자 조회
    UserEntity findByUserId(@Param("userId") int userId);

    // 회원 등록(INSERT)
    int insertUser(UserEntity user);

    // 회원정보 수정(UPDATE)
    int updateUser(UserEntity user);

    // 회원 탈퇴(DELETE or is_active='N')
    int deleteUser(@Param("userId") int userId);

    // reset_token, reset_token_expires 업데이트
    int updateResetToken(@Param("userId") Long userId,
                         @Param("token") String token,
                         @Param("expires") Date expires);

    // reset_token으로 사용자 조회
    UserEntity findByResetToken(@Param("token") String token);

    // 새 비밀번호 업데이트 + reset_token null
    int updatePasswordAndClearToken(@Param("userId") Long userId,
                                    @Param("newPasswordHash") String newPasswordHash);

    // 마이페이지 정보 조회 (기부 총액 등)
    MyPageResponseDTO findMyPageInfo(@Param("userId") Long userId);

    /**
     * 해당 userId의 총 기부액 합계
     */
    @Select("SELECT COALESCE(SUM(donation_amount), 0) FROM donation WHERE user_id = #{userId}")
    int sumDonationAmount(@Param("userId") int userId);
    
 // 전체 회원 조회
    @Select("SELECT " +
            "user_id AS userId, " +
            "email, " +
            "password_hash AS passwordHash, " +
            "user_name AS userName, " +
            "gender, " +
            "phone, " +
            "user_type AS userType, " +
            "balance, " +
            "created_at AS createdAt, " +
            "updated_at AS updatedAt, " +
            "is_active AS isActive, " +
            "profile_image AS profileImage, " +
            "reset_token AS resetToken, " +
            "reset_token_expires_at AS resetTokenExpiresAt " +
            "FROM user")
    List<UserEntity> findAll();
    
  
}
