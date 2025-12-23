package org.example.shopping.order;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    // 장바구니 주문 생성 기능

    // 주문 생성 기능
    public Order CreateOrder(OrderRequest.CreateDTO createDTO, User user) {
        Order order = createDTO.toEntity(user);

        return orderRepository.save(order);
    }

    // 주문 목록 조회
    public List<Order> getOrderList() {
        return orderRepository.findAllOrderByCreatedAt();
    }

    // 주문 상세 조회
    public Order getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("해당 주문을 찾을 수 없습니다."));
    }
}
