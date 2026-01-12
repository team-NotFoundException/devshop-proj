package org.example.shopping.order;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.users.User;
import org.example.shopping.users.user.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Order createOrder(Long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));

        Order order = Order.builder()
                .user(userEntity)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

       return orderRepository.save(order);
    }

    public List<OrderResponse.OrderListDTO> orderList(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserIdAndOrderByCreatedAtDesc(userId);

        return orderList.stream()
                .map(OrderResponse.OrderListDTO::new)
                .toList();
    }

    public OrderResponse.OrderDetailDTO orderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));

        return new OrderResponse.OrderDetailDTO(order);
    }
}
