package org.example.shopping.order;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cart.CartService;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    // 주문 생성 기능
//    public void CreateOrder(User user) {
//        Long userId = user.getId();
//
//        Cart cartEntity = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));
//
//        Long cartId = cartEntity.getId();
//
//        List<CartItem> checkItem = getChecked(cartId);
//
//        for (CartItem items : checkItem) {
//            OrderItem orderItem = OrderItem.builder()
//
//        }
//
//
//        orderRepository.save();
//    }



    // 주문 목록 조회
//    public List<Order> getOrderList() {
//        return orderRepository.findAllOrderByCreatedAt();
//    }

    // 주문 상세 조회
    public Order getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("해당 주문을 찾을 수 없습니다."));
    }

    // cartCheck 검증 편의
    private List<CartItem> getChecked(Long cartId){
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
}
