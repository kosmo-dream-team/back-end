package com.dream_on.springboot.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.UserDTO;
import com.dream_on.springboot.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminUserController {

    private final UserService userService;

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "user 0-" + (users.size() - 1) + "/" + users.size());
        headers.add("Access-Control-Expose-Headers", "Content-Range");
        return new ResponseEntity<>(users, headers, HttpStatus.OK);
    }


    // 단일 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") int userId) {
        UserEntity user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    

    // 회원 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") int userId, @RequestBody UserEntity user) {
        // UserEntity를 UserDTO로 변환
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUserId().intValue()); // 또는 userId를 직접 사용
        userDTO.setEmail(user.getEmail());
        userDTO.setUser_name(user.getUserName());
        userDTO.setGender(user.getGender());
        userDTO.setPhone(user.getPhone());
        userDTO.setUser_type(user.getUserType());
        userDTO.setBalance(user.getBalance());
        userDTO.setProfile_image(user.getProfileImage());
        // 필요시 나머지 필드도 변환

        userService.updateUser(userDTO);
        // 변환 후 다시 UserEntity를 조회해서 반환하거나, userDTO를 기반으로 변환 후 반환
        return ResponseEntity.ok(user);
    }

    // 회원 탈퇴 (삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
