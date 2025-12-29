package org.example.shopping.order;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.orderItem.OrderItemRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.service.PaymentService;
import org.example.shopping.users.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;


    // 주문 완료 화면
    // http://localhost:8080/order-complete
    @GetMapping("/order-complete")
    public String orderForm(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        return "order/result-form";
    }

    // 주문 생성 요청 기능
    @PostMapping("/order-create")
    public String orderProc(PaymentRequest.CreateDTO createDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        orderService.CreateOrder(sessionUser);
        paymentService.createPayment(sessionUser, createDTO);

        return "redirect:/order-complete";
    }

    // 주문목록 조회
    // http://localhost:8080/order/list
    @GetMapping("/order/list")
    public String orderList(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Long userId = sessionUser.getId();

        List<OrderResponse.OrderListDTO> orderList = orderService.orderList(userId);
        model.addAttribute("orderList", orderList);

        return "user/mypage-orderList";
    }

    // 주문상세 조회
    @GetMapping("/order/detail")
    public String detail(Long id, Model model) {

        List<OrderItem> orderItems = orderService.orderDetail(id);

        model.addAttribute("orderItems", orderItems);

        return "user/mypage-orderDetail";
    }

    // 구매확정
    @PostMapping("/order/{orderItemId}/confirm")
    @ResponseBody
    public OrderStatus confirmPurchase(@PathVariable Long orderItemId) {

        OrderItem orderItem = orderService.confirmPurchase(orderItemId);

        return orderItem.getOrderStatus();
    }
}