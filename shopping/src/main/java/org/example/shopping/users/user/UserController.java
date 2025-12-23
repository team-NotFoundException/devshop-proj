package org.example.shopping.users.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.cart.CartService;
import org.example.shopping.users.OAuthService;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final OAuthService oAuthService;

    @GetMapping("/user/kakao")
    public String kakaoCallback(@RequestParam("code") String code, HttpSession session) {
        User loginUser = oAuthService.loginWithKakao(code);
        session.setAttribute("sessionUser", loginUser);
        return "redirect:/";
    }



    // 로그인 화면 요청
    // http://localhost:8080/user/login
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
            return "redirect:/user/login";
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
    // http://localhost:8080/join
    @GetMapping("/user/join")
    public String JoinForm() {
        return "user/join-form";
    }

    @PostMapping("/user/join")
    public String signUp(
            @Valid @ModelAttribute UserRequest.SignUpDTO signUpDTO
    ) {

        User user = userService.signUp(signUpDTO);

        cartService.createCart(user);

        return "redirect:/user/login";
    }

    // ---------------------------------------- //

    // 회원정보 수정 화면 요청
    @GetMapping("/user/update")
    public String userUpdateView(
            HttpSession session,
            Model model
    ) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.userUpdateView(sessionUser.getId());
        model.addAttribute("sessionUser", user);

        return "user/mypage-update";
    }

    // 회원정보 수정 기능 요청
    @PostMapping("/user/update")
    public String userUpdate(
            @Valid @ModelAttribute UserRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User updateUser = userService.userUpdate(updateDTO, sessionUser.getId());

        session.setAttribute("sessionUser", updateUser);
        return "redirect:/";
    }

    // ---------------------------------------- //

    // 회원정보 조회

    // 로그인용 사용자 조회

    // 유저 전체 조회(권한 포함)

    // 유저 권한 부여

    // 유저 권한 회수
}
