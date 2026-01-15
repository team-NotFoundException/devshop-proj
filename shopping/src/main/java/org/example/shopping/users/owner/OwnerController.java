package org.example.shopping.users.owner;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.utils.ValidationGroups;
import org.example.shopping.product.ProductResponse;
import org.example.shopping.product.ProductService;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.users.OAuthService;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.owner.dto.OwnerRequest;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final OwnerService ownerService;
    private final ProductService productService;

    // 로그인 화면 요청
    // http://localhost:8080/user/login
//    @GetMapping("/owner/login")
//    public String loginForm() {
//        return "user/owner/login-form";
//    }
//
//    // 로그인 기능 요청
//    @PostMapping("/owner/login")
//    public String login(
//            @Valid @ModelAttribute UserRequest.LoginDTO loginDTO,
//            HttpSession session
//    ) {
//
//        try{
//            User sessionUser = ownerService.login(loginDTO);
//
//            session.setAttribute("sessionUser", sessionUser);
//
//            System.out.println("성공~");
//            return "redirect:/owner/dashboard";
//        } catch (Exception e) {
//            System.out.println("실패지롱");
//            return "redirect:/owner/login";
//        }
//
//    }

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
            @Validated(ValidationGroups.OwnerSignUpOrderGroup.class) @ModelAttribute OwnerRequest.OwnerSignUpDTO ownerSignUpDTO
    ) {

        ownerService.ownerSignUp(ownerSignUpDTO);

        return "redirect:/user/login";
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
            return "redirect:/user/login";
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
        return "redirect:/owner/dashboard";
    }

    @GetMapping("/owner/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        List<ProductResponse.ListDTO> allProducts = productService.findAll(sessionUser.getId());

        long totalCount = allProducts.size();

        long  activeCount = productService.findByStatus(ProductStatus.ACTIVE).size();

        long soldOutCount = productService.findByStatus(ProductStatus.SOLD_OUT).size();

        List<ProductResponse.ListDTO> recentProducts =  allProducts.stream().limit(5).collect(Collectors.toList());

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("soldOutCount", soldOutCount);
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("sessionUser", sessionUser);

        return "user/owner/main";
    }
}
