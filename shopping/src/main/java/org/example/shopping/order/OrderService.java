package org.example.shopping.order;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.orderItem.OrderItemRepository;
import org.example.shopping.product.Product;
import org.example.shopping.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 주문 생성 기능
    @Transactional
    public void CreateOrder(User user) {

        // 1. 주문 생성
        Order order = Order.builder()
                .user(user)
                .orderNumber("ss")
                .build();

        // 2. 유저 id로 카트 조회
        Cart cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cartEntity.getId();

        // 3. 조회한 카트에서 체크된 카트 아이템 조회
        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsChecked(cartId);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception404("체크된 상품이 없습니다.");
        }

        // 4. 주문 아이템으로 복사 (builder로 생성)
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .product(cartItem.getProduct())
                        .productPrice(cartItem.getProduct().getPrice())
                        .productName(cartItem.getProduct().getProductName())
                        .quantity(cartItem.getQuantity())
                        .totalPrice(cartItem.getTotalPrice())
                        .build())
                .toList();

        // 5. 주문한 아이템 재고 차감 및 주문에 주문 아이템 추가
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new Exception400("재고가 부족한 상품입니다.");
            }
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            order.addItem(item);
        }

        orderRepository.save(order);
    }


    // 주문 목록 조회
    public List<OrderResponse.OrderListDTO> orderList(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserIdAndOrderByCreatedAtDesc(userId);

        return orderList.stream()
                .map(order -> new OrderResponse.OrderListDTO(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream()
                                .map((orderItem -> new OrderResponse.OrderItemListDTO(
                                        orderItem.getId(),
                                        orderItem.getProduct(),
                                        orderItem.getProductName(),
                                        orderItem.getProductPrice(),
                                        orderItem.getQuantity(),
                                        orderItem.getOrderStatus(),
                                        orderItem.getTotalPrice(),
                                        orderItem.getIsComplete())
                                )).toList()
                ))
                .toList();
    }

    // 주문 상세 조회
    public List<OrderItem> orderDetail(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));

        return order.getOrderItems();
    }

    // 구매 확정
    @Transactional
    public void confirmPurchase(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new Exception404("물건을 찾을 수 없습니다."));
        orderItem.confirmStatus();

        orderItemRepository.save(orderItem);
    }
}