package org.example.shopping.users.admin;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    // 유저 전체 조회

    // 상품 전체 조회

    // 그냥 다 조회
}
