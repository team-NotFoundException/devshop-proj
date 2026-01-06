package org.example.shopping.users.admin;


import jakarta.servlet.http.HttpSession;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/main")
    public String dashboard(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("user", sessionUser);
        return "user/admin/main";
    }

    @GetMapping("/admin/list")
    public String userList(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("sessionUser");


        return "user/admin/user-list";
    }

    // 로그인

    // 로그아웃

    // 유저 전체 조회

    // 상품 전체 조회

    // 그냥 다 조회
}
