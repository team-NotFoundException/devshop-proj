package org.example.shopping.order;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cart.CartService;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.orderItem.OrderItemRepository;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.service.PaymentService;
import org.example.shopping.payment.service.gateway.PaymentGateway;
import org.example.shopping.payment.service.gateway.PaymentGatewayResolver;
import org.example.shopping.payment.service.gateway.PaymentResult;
import org.example.shopping.product.Product;
import org.example.shopping.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final PaymentService paymentService;


    // 주문 생성 기능 (Mock 결제)
    @Transactional
    public void createOrderByMock(User user, PaymentRequest.CreateDTO createDTO) {

        // 유저 id로 카트 조회
        Cart cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cartEntity.getId();

        // 조회한 카트에서 체크된 카트 아이템 조회
        List<CartItem> cartItems = getChecked(cartId);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception404("체크된 상품이 없습니다.");
        }

        paymentService.processMockPayment(user, cartId, createDTO);

        cartItemRepository.deleteAll(cartItems);
        cartService.updateTotalPrice(cartId);
    }

    // 주문 생성 기능 (Toss 결제)
    @Transactional
    public void createOrderByToss(User user, PaymentRequest.ApproveDTO approveDTO) {

        // 2. 유저 id로 카트 조회
        Cart cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cartEntity.getId();

        // 3. 조회한 카트에서 체크된 카트 아이템 조회
        List<CartItem> cartItems = getChecked(cartId);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception404("체크된 상품이 없습니다.");
        }

        paymentService.approvePayment(user, cartId, approveDTO);

        cartItemRepository.deleteAll(cartItems);
        cartService.updateTotalPrice(cartId);
    }



    // cartCheck 검증 편의
    private List<CartItem> getChecked(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<CartItem> checkItem = cartItems.stream()
                .filter(CartItem::isItemChecked)
                .toList();
        if (checkItem.isEmpty()) {
            throw new Exception400("결제할 상품을 선택해 주세요");
        }
        return checkItem;
    }

    // 주문 목록 조회
    public List<OrderResponse.OrderListDTO> orderList(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserIdAndOrderByCreatedAtDesc(userId);

        return orderList.stream()
                .map(order -> new OrderResponse.OrderListDTO(
                        order.getId(),
                        order.getOrderNumber(),
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
                                        orderItem.getIsComplete(),
                                        orderItem.getIsRefund())
                                )).toList()
                ))
                .toList();
    }

    // 주문 상세 조회
    public List<OrderResponse.OrderDetailDTO> orderDetail(Long orderId) {

        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));

        List<OrderResponse.OrderDetailDTO> orderItems = order.getOrderItems().stream()
                .map(OrderResponse.OrderDetailDTO::new
                ).toList();

        return orderItems;
    }

    // 구매 확정
    @Transactional
    public void confirmPurchase(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new Exception404("물건을 찾을 수 없습니다."));
        orderItem.confirmStatus();

        orderItemRepository.save(orderItem);
    }

    @Transactional
    public void refundPurchase(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new Exception404("물건을 찾을 수 없습니다."));
        orderItem.refundStatus();

        orderItemRepository.save(orderItem);
    }
}