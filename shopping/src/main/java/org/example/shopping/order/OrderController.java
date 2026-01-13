package org.example.shopping.order;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/list")
    public String list(Model model, HttpSession session, @RequestParam(required = false) String keyword){
        User sessionUser = (User) session.getAttribute("sessionUser");

        List<OrderResponse.OrderListDTO> orderList = orderService.orderList(sessionUser.getId(), keyword);

        model.addAttribute("orderList", orderList);

        return "/user/mypage-orderList";
    }

    @GetMapping("/order/detail")
    public String detail(Model model, HttpSession session, Long orderId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        OrderResponse.OrderDetailDTO order = orderService.orderDetail(orderId);

        model.addAttribute("order", order);

        return "user/mypage-orderDetail";
    }
}
