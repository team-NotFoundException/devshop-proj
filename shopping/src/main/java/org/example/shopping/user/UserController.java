package org.example.shopping.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // 로그인 화면 요청
    // http://localhost:8080/login
    @GetMapping
    public String loginForm() {
        return "user/login-form";
    }

    // 로그인 기능 요청
    @PostMapping("/login")
    public String loginProc(@Valid UserRequest.LoginDTO loginDTO, HttpSession session) {
        try {
            User sessionUser = userRepository.findByUsernameAndPassword(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            );
        } catch (Exception e) {
           throw new IllegalArgumentException("id 또는 pw가 올바르지 않습니다.");
        }
    }

    // ---------------------------------------- //

    // 로그아웃

    // ---------------------------------------- //

    // 회원가입 화면 요청

    // 회원가입 기능 요청

    // ---------------------------------------- //

    // 회원정보 수정 화면 요청

    // 회원정보 수정 기능 요청

    // ---------------------------------------- //

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수
}
