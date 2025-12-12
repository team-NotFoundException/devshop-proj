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

    private final OrderPersistRepository orderPersistRepository;

    // 주문 생성 요청 화면
    @GetMapping("/order/create")
    public String orderForm(Model model, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인 시 이용가능합니다.");
        }

        return "order/create-form";
    }

    // 장바구니 주문 생성 요청 기능
    @PostMapping("/order/create-cart")
    public String orderCartProc(HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인 시 이용가능합니다.");
        }
        return "redirect:result-form";
    }

    // 주문 생성 요청 기능
    @PostMapping("/order/create")
    public String orderProc(OrderRequest.CreateDTO createDTO, HttpSession session) {
        String sessionUser = (String) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인 시 이용가능합니다.");
        }

        Order order = createDTO.toEntity(sessionUser);
        orderPersistRepository.save(order);
        return "redirect:result-form";
    }


    // 주문목록 조회
    @GetMapping("/order/list")
    public String orderList(Model model) {
        List<Order> orderList = orderPersistRepository.findAll();
        model.addAttribute("orderList", orderList);
        return "order/list";
    }

    // 주문상세 조회
    @GetMapping("/order/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Order order = orderPersistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id의 정보를 찾을 수 없습니다."));

        model.addAttribute(order);
        return "/order/list";
    }
}