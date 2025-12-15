package org.example.shopping.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            if (sessionUser == null) {
                throw new IllegalArgumentException("사용자명 또는 비밀번호가 올바르지 않아요");
            }
            session.getAttribute("sessionUser");
            return "redirect:/";
        } catch (Exception e) {
            return "user/login-form";
        }
    }

    // ---------------------------------------- //

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------------------------------------- //

    // 회원가입 화면 요청
    @GetMapping("/join")
    public String JoinForm() {
        return "user/join-form";
    }

    // 회원가입 기능 요청
    @PostMapping("/join")
    public String joinProc(@Valid UserRequest.SignUpDTO signUpDTO) {
        User existingUser = userRepository.findByUsername(signUpDTO.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입낟");
        }

        User user = signUpDTO.toEntity();

        userRepository.save(user);

        return "redirect:/login";
    }

    // ---------------------------------------- //

    // 회원정보 수정 화면 요청
    @GetMapping("/user/update")
    public String updateForm(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            System.out.println("로그인 하지 않은 사용자");
            return "redirect:/login";
        }

        User user =  userRepository.findById(sessionUser.getId());
        model.addAttribute("user", user);

        return "user/update-form";
    }

    // 회원정보 수정 기능 요청

    // ---------------------------------------- //

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수
}
