package org.example.shopping.users.admin;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception500;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserResponse;
import org.example.shopping.users.enums.RoleType;
import org.example.shopping.users.owner.OwnerService;
import org.example.shopping.users.owner.dto.OwnerResponse;
import org.example.shopping.users.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final OwnerService ownerService;

    @GetMapping("/admin/main")
    public String dashboard(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 최근 10명 최신순 조회
        List<UserResponse.UserList> recentList = userService.topTenList();
        model.addAttribute("recentUsers", recentList);
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


    // 회원삭제
    @PostMapping("/admin/user/{userId}/delete")
    public String deleteUser(HttpSession session,@PathVariable Long userId){
        User sessionUser= (User) session.getAttribute("sessionUser");
        userService.deleteUser(userId);
        return "redirect:/admin/list";
    }


    // owner 전체 조회
    @GetMapping("/admin/owner/list")
    public String ownerList(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<OwnerResponse.OwnerList> ownerList = ownerService.ownerList();
        model.addAttribute("ownerList", ownerList);

        return "/user/admin/owner-list";
    }

    // 입점사 승인상태변경
    @PostMapping("/admin/owner/{ownerId}/approve")
    public String approveStatus(HttpSession session, @PathVariable Long ownerId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ownerService.approveStatus(ownerId);

        return "redirect:/admin/owner/list";
    }
    // 입점사 정지상태변경
    @PostMapping("/admin/owner/{ownerId}/suspension")
    public String suspensionStatus(HttpSession session, @PathVariable Long ownerId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ownerService.suspensionStatus(ownerId);

        return "redirect:/admin/owner/list";
    }

    // 입점사 상세 조회
    @GetMapping("/admin/owner/{ownerId}/detail")
    @ResponseBody
    public OwnerResponse.OwnerDetail ownerDetail(@PathVariable Long ownerId) {
        return ownerService.ownerDetail(ownerId);
    }

    // owner 전체 조회

    // 상품 전체 조회

    // 그냥 다 조회
}
