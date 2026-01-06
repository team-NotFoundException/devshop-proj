package org.example.shopping.users.admin;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception500;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.enums.RoleType;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin/main")
    public String dashboard(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("user", sessionUser);
        return "user/admin/main";
    }

    @GetMapping("/admin/list")
    public String userList(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<UserResponse.UserList> userList = userService.userList();
        model.addAttribute("userList", userList);

        return "user/admin/user-list";
    }

    // 권한부여
    @PostMapping("/admin/grant-role")
    @ResponseBody
    public String grantRole(@RequestParam Long userId, @RequestParam String role, HttpSession session){
        User sessionUser = (User) session.getAttribute("sessionUser");
        try {
            RoleType roleType = RoleType.valueOf(role.toUpperCase());
            userService.grantRole(userId, roleType);
            return "redirect:/user/admin/user-list";

        }catch (Exception e){
            throw new Exception500(e.getMessage());
        }

    }

    // 유저 전체 조회

    // 상품 전체 조회

    // 그냥 다 조회
}
