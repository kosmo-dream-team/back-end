package com.dream_on.springboot.mapper;

import java.sql.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dream_on.springboot.domain.UserEntity;

@Mapper
public interface UserMapper {

    // 이메일로 사용자 조회
    UserEntity findByEmail(@Param("email") String email);

    // user_id 로 사용자 조회
    UserEntity findByUserId(@Param("userId") int i);

    // 회원 등록(INSERT)
    int insertUser(UserEntity user);

    // 회원정보 수정(UPDATE)
    int updateUser(UserEntity user);

    // 회원 탈퇴(DELETE or is_active='N')
    // 예시: 소프트 삭제 -> update user set is_active = 'N'
    int deleteUser(@Param("userId") int userId);

    // 새로 추가: reset_token, reset_token_expires 업데이트
    int updateResetToken(@Param("userId") Long userId,
                         @Param("token") String token,
                         @Param("expires") Date expires);

    // 새로 추가: reset_token 으로 사용자 조회
    UserEntity findByResetToken(@Param("token") String token);

    // 새 비밀번호 업데이트 + reset_token null
    int updatePasswordAndClearToken(@Param("userId") Long userId,
                                    @Param("newPasswordHash") String newPasswordHash);

}
