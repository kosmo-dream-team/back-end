package com.dream_on.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream_on.springboot.domain.UserEntity;
import com.dream_on.springboot.dto.LoginRequestDTO;
import com.dream_on.springboot.dto.LoginResponseDTO;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordResetService passwordResetService;



    /**
     * 회원정보 수정 (PUT /api/update/userProfile)
     * 
     * - @PutMapping으로 변경
     * - @RequestBody로 DTO를 받음
     * - 세션 대신 단순히 user_id를 DTO로 받는다고 가정
     */
    @PutMapping("/update/userProfile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDTO userDTO) {
        try {
            // user_id가 0 이거나 없는 경우 예외
            if (userDTO.getUser_id() == 0) {
                throw new IllegalArgumentException("수정할 사용자의 user_id가 없습니다.");
            }

            // 실제 서비스 로직 호출
            userService.updateUser(userDTO);

            // 성공 시
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "회원정보가 성공적으로 수정되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 시
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("errorMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    
    /**
     * 회원가입 처리: POST /api/signup
     * 프론트엔드에서 보내는 JSON 예:
     * {
     *   "email": "test_signup@example.com",
     *   "user_name": "홍길동",
     *   "password_hash": "temp",
     *   "phone": "0101",
     *   "gender": "남성",
     *   "user_type": "donor"
     * }
     */
    @PostMapping("/signup")
    @ResponseBody
    public Map<String, Object> signup(@RequestBody UserDTO userDTO, Model model) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 회원가입 처리 (이메일 중복 체크, 패스워드 암호화 및 DB 등록)
            userService.registerUser(userDTO);
            response.put("success", true);
            response.put("message", "회원가입 성공");
            return response;
        } catch (IllegalArgumentException e) {
            // 이메일 중복 등으로 인한 예외 처리
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원가입 중 오류: " + e.getMessage());
            return response;
        }
    }    
    

  /**
   * 로그인 API
   * 
   * @param request JSON: { "email": "...", "password_hash": "..." }
   * @return JSON: LoginResponseDTO or 에러
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
	  System.out.println("111111111111");
      LoginResponseDTO result = userService.login(dto);
      if (result == null) {
          // 로그인 실패
          return ResponseEntity.status(400).body("로그인 실패: 이메일/비밀번호 불일치");
      }
      // 로그인 성공
      return ResponseEntity.ok(result);
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

    /**
     * 회원가입 처리: POST /api/signup
     * 프론트엔드에서 보내는 JSON 예:
     * {
     *   "email": "temp@temp.com",
     *   "user_name": "이름",
     *   "password_hash": "비번",
     *   "phone": "0101",
     *   "gender": "남성",
     *   "user_type": "donor"
     * }
     
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupDTO) {
        try {
            // userService.registerUser(...) 안에서 회원가입 로직 수행
            userService.registerUser(signupDTO);

            // 회원가입 성공 시
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // 회원가입 실패 시
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "회원가입 중 오류가 발생했습니다.");
            error.put("errorDetail", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
     */    

    /**
     * 회원가입 처리 (POST /user/register)

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
     */    


    /**
     * 회원정보 수정 (POST /user/update)

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
     */
    

}
