package org.example.shopping.order;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/list")
    public String list(Model model, HttpSession session){
        User sessionUser = (User) session.getAttribute("sessionUser");

        List<OrderResponse.OrderListDTO> orderList = orderService.orderList(sessionUser.getId());

        model.addAttribute("orderList", orderList);

        return "/user/mypage-orderList";
    }

}
