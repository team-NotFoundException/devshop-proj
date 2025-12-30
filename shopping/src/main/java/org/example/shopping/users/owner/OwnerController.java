package org.example.shopping.users.owner;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.OAuthService;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final OwnerService ownerService;

    // 로그인 화면 요청
    // http://localhost:8080/user/login
    @GetMapping("/owner/login")
    public String loginForm() {
        return "user/owner/login-form";
    }

    // 로그인 기능 요청
    @PostMapping("/owner/login")
    public String login(
            @Valid @ModelAttribute UserRequest.LoginDTO loginDTO,
            HttpSession session
    ) {

        try{
            User sessionUser = ownerService.login(loginDTO);

            session.setAttribute("sessionUser", sessionUser);

            System.out.println("성공~");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("실패지롱");
            return "redirect:/owner/login";
        }

    }

    // ---------------------------------------- //

    // 로그아웃
    @GetMapping("/owner/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------------------------------------- //

    // 회원가입 화면 요청
    // http://localhost:8080/join
    @GetMapping("/owner/join")
    public String JoinForm() {
        return "user/owner/join-form";
    }

    @PostMapping("/owner/join")
    public String signUp(
            @Valid @ModelAttribute UserRequest.SignUpDTO signUpDTO
    ) {

        ownerService.signUp(signUpDTO);

        return "redirect:/owner/login";
    }

    // ---------------------------------------- //

    // 회원정보 수정 화면 요청
    @GetMapping("/owner/update")
    public String userUpdateView(
            HttpSession session,
            Model model
    ) {

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return "redirect:/owner/login";
        }
        User user = userService.userUpdateView(sessionUser.getId());
        model.addAttribute("sessionUser", user);

        return "owner/owner-update";
    }

    // 회원정보 수정 기능 요청
    @PostMapping("/owner/update")
    public String userUpdate(
            @Valid @ModelAttribute UserRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User updateUser = userService.userUpdate(updateDTO, sessionUser.getId());

        session.setAttribute("sessionUser", updateUser);
        return "redirect:/";
    }

    // 상품 전체 조회

    // 상품 수정

    // 상품 삭제

    //
}
