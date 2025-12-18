package org.example.shopping.order;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    // 주문 완료 화면
    // http://localhost:8080/order-complete
    @GetMapping("/order-complete")
    public String orderForm(HttpSession session) {
//        String sessionUser = (String) session.getAttribute("sessionUser");

        return "order/create-form";
    }

    // 장바구니 주문 생성 요청 기능
    @PostMapping("/order-create-inCart")
    public String orderCartProc(HttpSession session) {
//        String sessionUser = (String) session.getAttribute("sessionUser");


        // 나중에 추가
        return "redirect:result-form";
    }

    // 주문 생성 요청 기능
    @PostMapping("/order-create")
    public String orderProc(OrderRequest.CreateDTO createDTO, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");

        orderService.CreateOrder(createDTO, sessionUser);

        return "redirect:result-form";
    }

    // 주문목록 조회
    // http://localhost:8080/order/list
    @GetMapping("/order-list")
    public String orderList(Model model) {

        orderService.getOrderList();

        return "order/list";
    }

    // 주문상세 조회
    @GetMapping("/order/{id}")
    public String detail(@PathVariable Long id, Model model) {

        orderService.getOrderDetail(id);

        return "/order/list";
    }
}