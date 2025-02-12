package com.dream_on.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.UserDTO;
import com.dream_on.springboot.service.PasswordResetService;
import com.dream_on.springboot.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordResetService passwordResetService;

    /**
     * 1) 로그인 화면 이동
     * GET /user/login
     */
    @GetMapping("/login")
    public String loginForm() {
        // 뷰 템플릿 (예: login.jsp or login.html)으로 이동
        return "user/loginForm"; 
        // 예: /WEB-INF/view/user/loginForm.jsp
    }

    /**
     * 2) 로그인 처리
     * POST /user/login
     */
//    @PostMapping("/login")
//    public String login(@ModelAttribute UserDTO userDTO, 
//                        HttpSession session, 
//                        Model model) {
//        try {
//            UserEntity user = userService.login(userDTO.getEmail(), userDTO.getPassword());
//            if (user != null) {
//                // 로그인 성공
//                // 세션에 사용자 정보 저장 (ID, username 등)
//                session.setAttribute("loginUser", user);
//
//                // 메인 페이지 등으로 리다이렉트
//                return "redirect:/";
//            } else {
//                // 로그인 실패 시 에러 메시지
//                model.addAttribute("errorMsg", "이메일 또는 비밀번호가 올바르지 않습니다.");
//                return "user/loginForm"; 
//            }
//        } catch (Exception e) {
//            // 기타 예외 처리
//            model.addAttribute("errorMsg", "로그인 중 오류가 발생했습니다: " + e.getMessage());
//            return "user/loginForm";
//        }
//    }

    /**
     * React 별도 클라이언트, 서버-클라이언트 간 REST 통신
     * React 에서 fetch("/login", { ... }) 후 response.json() 으로 받아서 data.username 등 추출.
     */    
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody UserDTO userDTO, HttpSession session) {
        UserEntity user = userService.login(userDTO.getEmail(), userDTO.getPassword_hash());
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            session.setAttribute("loginUser", user);
            response.put("success", true);
            response.put("username", user.getUsername());
            response.put("rank", user.getRank());
            response.put("profileImage", user.getProfileImage());
        } else {
            response.put("success", false);
            response.put("errorMsg", "이메일/비번 불일치");
        }
        return response;
    }
    
    
    /**
     * 3) 회원가입 화면 이동
     * GET /user/register
     */
    @GetMapping("/register")
    public String registerForm() {
        return "user/registerForm"; 
        // 예: /WEB-INF/view/user/registerForm.jsp
    }

    /**
     * 4) 회원가입 처리
     * POST /user/register
     */
    @PostMapping("/register")
    public String register(@ModelAttribute UserDTO userDTO,
                           Model model) {
        try {
            userService.registerUser(userDTO);
            // 회원가입 성공 → 로그인 페이지로 이동 or 메인 페이지 등
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            // 비번 불일치 등 사용자 입력 오류
            model.addAttribute("errorMsg", e.getMessage());
            return "user/registerForm";
        } catch (Exception e) {
            // 기타 예외
            model.addAttribute("errorMsg", "회원가입 중 오류: " + e.getMessage());
            return "user/registerForm";
        }
    }

    /**
     * 5) 로그아웃
     * GET /user/logout (또는 POST)
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 무효화
        session.invalidate();
        // 로그아웃 후 메인 페이지나 로그인 페이지로 리다이렉트
        return "redirect:/";
    }

    /**
     * 6) 회원정보 수정
     * POST
     */
    @PostMapping("/update")
    public String update(@ModelAttribute UserDTO userDTO,
                         HttpSession session,
                         Model model) {
        // 로그인 체크, userId 비교, etc.
        try {
            userService.updateUser(userDTO);
            return "redirect:/user/mypage";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "user/updateForm";
        }
    }

    /**
     * 7) 회원 탈퇴
     * GET 또는 POST
     */
    @GetMapping("/delete")
    public String deleteUser(@RequestParam int userId,
                             HttpSession session,
                             Model model) {
        // 로그인/권한 체크
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if(loginUser == null) {
            model.addAttribute("errorMsg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }
        if(!loginUser.getUserId().equals(userId)) {
            model.addAttribute("errorMsg", "본인만 탈퇴 가능합니다.");
            return "redirect:/";
        }

        try {
            userService.deleteUser(userId);
            // 세션 무효화
            session.invalidate();
            return "redirect:/?msg=탈퇴가 완료되었습니다.";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "회원 탈퇴 오류: " + e.getMessage());
            return "redirect:/user/mypage";
        }
    }

    /**
     * 8) 비밀번호 찾기
     * (1) 비밀번호 찾기 페이지 (GET /user/forgot-password)
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        // 폼(JSP/Thymeleaf 등) 반환
        return "user/forgotPasswordForm";
    }
    
    /**
     * 8) 비밀번호 찾기
     * (2) 비밀번호 찾기 요청 처리 (POST /user/forgot-password)
     */
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        try {
            passwordResetService.sendResetLink(email);
            model.addAttribute("msg", "비밀번호 재설정 링크가 이메일로 발송되었습니다.");
            return "user/forgotPasswordConfirm"; // 안내 페이지
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "user/forgotPasswordForm";
        }
    }

    /**
     * 8) 비밀번호 찾기
     * (3) 비밀번호 재설정 화면 (GET /user/reset-password)
     */
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        // 여기서 token 유효 여부를 미리 검증해도 되고,
        // 바로 resetPasswordForm.jsp 로 이동 후 POST로 최종 처리
        return "user/resetPasswordForm";
    }

    /**
     * 8) 비밀번호 찾기
     * (4) 비밀번호 재설정 처리 (POST /user/reset-password)
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                @RequestParam String newPasswordConfirm,
                                Model model) {
        if(!newPassword.equals(newPasswordConfirm)) {
            model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
            return "user/resetPasswordForm";
        }
        try {
            passwordResetService.resetPassword(token, newPassword);
            model.addAttribute("msg", "비밀번호가 성공적으로 재설정되었습니다. 로그인해주세요.");
            return "user/loginForm";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "user/resetPasswordForm";
        }
    }
    
}
