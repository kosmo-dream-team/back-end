package com.dream_on.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.MyPageResponseDTO;
import com.dream_on.springboot.dto.UserDTO;
import com.dream_on.springboot.security.CustomUserDetails;
import com.dream_on.springboot.service.PasswordResetService;
import com.dream_on.springboot.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * UserController
 *
 * <p>회원 관련 기능(로그인, 회원가입, 탈퇴, 비번찾기, 마이페이지 등)을 처리</p>
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordResetService passwordResetService;

    /**
     * 회원가입 처리 (POST /user/register)
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(@RequestBody UserDTO userDTO,
                           Model model) {
    	Map<String, Object> response = new HashMap<>();
        try {
        	System.out.println("userDTO : " + userDTO);
            userService.registerUser(userDTO);
            // 회원가입 성공 → 로그인 페이지
//            return "redirect:/user/login";
            response.put("success", true);
            return response;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
//            return "user/registerForm";
            response.put("success", false);
            return response;
        } catch (Exception e) {
            model.addAttribute("errorMsg", "회원가입 중 오류: " + e.getMessage());
//            return "user/registerForm";
            response.put("success", false);
            return response;
        }
    }

    /**
     * 로그인 (예: JSON 응답, React)
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody UserDTO userDTO, HttpSession session) {
    	System.out.println("1111111111111111");
    	System.out.println("user email : " + userDTO.getEmail());
    	System.out.println("user passwordhash : " + userDTO.getPassword_hash());
        UserEntity user = userService.login(userDTO.getEmail(), userDTO.getPassword_hash());
        Map<String, Object> response = new HashMap<>();
        System.out.println("22222222222222222");
        if (user != null) {
            session.setAttribute("loginUser", user);
            response.put("success", true);
            response.put("userName", user.getUserName());
            response.put("profileImage", user.getProfileImage());
        } else {
            response.put("success", false);
            response.put("errorMsg", "이메일/비밀번호 불일치");
        }
        System.out.println("3333333333333333333");
        return response;
    }

    /**
     * 회원정보 수정 (POST /user/update)
     */
    @PostMapping("/update")
    public String update(@ModelAttribute UserDTO userDTO,
                         HttpSession session,
                         Model model) {
        // 로그인 사용자 체크
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            model.addAttribute("errorMsg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }
        // 본인 확인 or 관리자 권한 체크
        if (!loginUser.getUserId().equals((long) userDTO.getUser_id())) {
            model.addAttribute("errorMsg", "본인만 수정 가능합니다.");
            return "redirect:/";
        }

        try {
            userService.updateUser(userDTO);
            return "redirect:/user/mypage";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "user/updateForm";
        }
    }

    /**
     * 회원 탈퇴 (GET /user/delete)
     */
    @GetMapping("/delete")
    public String deleteUser(@RequestParam int userId,
                             HttpSession session,
                             Model model) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if(loginUser == null) {
            model.addAttribute("errorMsg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }
        if(!loginUser.getUserId().equals((long)userId)) {
            model.addAttribute("errorMsg", "본인만 탈퇴 가능합니다.");
            return "redirect:/";
        }

        try {
            userService.deleteUser(userId);
            session.invalidate();
            return "redirect:/?msg=탈퇴완료";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "회원 탈퇴 오류: " + e.getMessage());
            return "redirect:/user/mypage";
        }
    }

    /**
     * 마이페이지 (GET /user/mypage)
     */
    @GetMapping("/mypage")
    @ResponseBody
    public ResponseEntity<MyPageResponseDTO> getMyPageInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return ResponseEntity.status(401).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        
        MyPageResponseDTO dto = userService.getMyPageInfo(userId);
        return ResponseEntity.ok(dto);
    }

    // 기타: 로그인 폼, 로그아웃, 비밀번호 찾기 등은 기존 코드 유지
}
