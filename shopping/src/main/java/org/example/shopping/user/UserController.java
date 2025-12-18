package org.example.shopping.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 로그인 화면 요청
    // http://localhost:8080/login
    @GetMapping("/user/login")
    public String loginForm() {
        return "user/login-form";
    }

    // 로그인 기능 요청
    @PostMapping("/user/login")
    public String login(
            @Valid @ModelAttribute UserRequest.LoginDTO loginDTO,
            HttpSession session
    ) {

        try{
            User sessionUser = userService.login(loginDTO);

            session.setAttribute("sessionUser", sessionUser);

            System.out.println("성공~");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("실패지롱");
            return "redirect:/";
        }

    }

    // ---------------------------------------- //

    // 로그아웃
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------------------------------------- //

    // 회원가입 화면 요청
    // http://localhost:8080/users/join
    @GetMapping("/user/join")
    public String JoinForm() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String signUp(
            @Valid @ModelAttribute UserRequest.SignUpDTO signUpDTO
    ) {

        userService.signUp(signUpDTO);

        return "redirect:/users/login";
    }

    // ---------------------------------------- //

    // 회원정보 수정 화면 요청
    // http://localhost:8080/users/edit
    @GetMapping("/user/edit")
    public String userUpdateView(
            @PathVariable Long id,
            @SessionAttribute("userSessionId") Long userSessionId,
            Model model
    ) {
        User userEntity = userService.userUpdateView(userSessionId);

        model.addAttribute("user", userEntity);

        return "layout/mypage-sidebar";
    }

    // 회원정보 수정 기능 요청
    @PostMapping("/user/edit")
    public String userUpdate(
            @Valid @ModelAttribute UserRequest.UpdateDTO updateDTO,
            @SessionAttribute("userSessionId") Long userSessionId
    ) {
        userService.userUpdate(updateDTO, userSessionId);

        return "user/update-form";
    }

    // ---------------------------------------- //

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수
}
