package org.example.shopping.users.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.cart.CartService;
import org.example.shopping.users.OAuthService;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.enums.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final OAuthService oAuthService;

    @Value("${address.juso.key}")
    private String jusoKey;

    @GetMapping("/kakao")
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
        System.out.println("DTO: " + signUpDTO);
        System.out.println("Address: " + signUpDTO.getAddress());

        User user = userService.signUp(signUpDTO);

        cartService.createCart(user);

        return "redirect:/user/login";
    }

    @GetMapping("/popup/juso")
    public String jusoPopupGet(Model model, HttpServletRequest request) {
        model.addAttribute("confmKey", jusoKey);
        model.addAttribute("returnUrl", request.getRequestURL());
        return "user/popup/juso-popup";
    }


    // POST: 주소 API에서 데이터를 받아서 GET으로 리다이렉트
    @PostMapping("/popup/juso")
    public String jusoPopupPost(UserResponse.JusoResponseDTO jusoResponse)
            throws UnsupportedEncodingException {

        System.out.println("=== POST 받은 데이터 ===");
        System.out.println("zipNo: " + jusoResponse.getZipNo());
        System.out.println("roadAddrPart1: " + jusoResponse.getRoadAddrPart1());
        System.out.println("addrDetail: " + jusoResponse.getAddrDetail());

        // DTO 내부 메서드로 URL 생성
        return jusoResponse.buildRedirectUrl();
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

        if (user.getGender() != null) {
            model.addAttribute("isMale", user.getGender() == Gender.M);
            model.addAttribute("isFeMale", user.getGender() == Gender.F);
        }
        model.addAttribute("isNone", user.getGender() == null);

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
