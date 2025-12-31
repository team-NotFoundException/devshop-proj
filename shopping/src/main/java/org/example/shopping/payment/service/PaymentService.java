package org.example.shopping.payment.service;

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
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.PaymentRefundRepository;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.error.BusinessException;
import org.example.shopping.payment.error.ErrorCode;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.payment.service.gateway.PaymentGateway;
import org.example.shopping.payment.service.gateway.PaymentGatewayResolver;
import org.example.shopping.payment.service.gateway.PaymentResult;
import org.example.shopping.users.User;
import org.example.shopping.users.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayResolver gatewayResolver;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;


    // ================== 카트 정보 가져오기 ================

    public PaymentResponse.CartPaymentDTO getCartInfo(Long cartId) {
        List<CartItem> checkItem = getChecked(cartId);
        List<PaymentResponse.CartItemPaymentDTO> itemsInfo = checkItem.stream()
                .map(item -> new PaymentResponse.CartItemPaymentDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getProductCode(),
                        item.getProduct().getProductName(),
                        item.getQuantity(),
                        item.getProduct().getPrice(),
                        item.getTotalPrice()
                ))
                .toList();

        Long totalAmount = checkItem.stream()
                .mapToLong(CartItem::getTotalPrice)
                .sum();
        return new PaymentResponse.CartPaymentDTO(cartId, itemsInfo, totalAmount);
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


    // ===================== 결제 =====================
    @Transactional
    public void createPayment(
            User sessionUser, PaymentRequest.CreateDTO createDTO) {
        Cart cartEntity = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));

        Long cartId = cartEntity.getId();

        switch (createDTO.getMethod()) {
            case MOCK -> processMockPayment(sessionUser, cartId, createDTO);
            case TOSS_PAY -> throw new BusinessException(ErrorCode.INVALID_INPUT, "TOSS_PAY");
        }
    }

    private void processMockPayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        List<CartItem> checkItem = getChecked(cartId);
        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .orderId("ORD-" + UUID.randomUUID())
                    .paymentKey("MOCK-" + UUID.randomUUID())
                    .amount(item.getTotalPrice())
                    .method(createDTO.getMethod())
                    .status(PaymentStatus.SUCCESS)
                    .productCode(item.getProduct().getProductCode())
                    .productName(item.getProduct().getProductName() + "(수량: " + item.getQuantity() + "개")
                    .build();
            payment.paySuccess();
            paymentRepository.save(payment);
        }
        cartItemRepository.deleteAll(checkItem);
        cartService.updateTotalPrice(cartId);
    }

    @Transactional
    public PaymentResponse.PaymentResultDTO approvePayment(User sessionUser, Long cartId, PaymentRequest.ApproveDTO approveDTO) {
        List<CartItem> checkItem = getChecked(cartId);
        PaymentGateway gateway = gatewayResolver.resolve(approveDTO.getMethod());
        PaymentResult paymentResult = gateway.approve(approveDTO);

        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .orderId(approveDTO.getOrderId() + "-" + item.getId())
                    .paymentKey(approveDTO.getPaymentKey())
                    .amount(item.getTotalPrice())
                    .method(approveDTO.getMethod())
                    .status(paymentResult.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                    .productCode(item.getProduct().getProductCode())
                    .productName(item.getProduct().getProductName())
                    .failureCode(paymentResult.getFailureCode())
                    .failureMessage(paymentResult.getFailureMessage())
                    .build();
            if (paymentResult.isSuccess()) {
                payment.paySuccess();
                if (cartId != null) {
                    cartService.removeCheckedCartItem(cartId);
                }
            }
            paymentRepository.save(payment);
        }


        List<PaymentResponse.PaymentResultDTO.PaymentItemDTO> items = checkItem.stream()
                .map(item -> {
                    PaymentResponse.PaymentResultDTO.PaymentItemDTO dto =
                            new PaymentResponse.PaymentResultDTO.PaymentItemDTO();
                    dto.setProductName(item.getProduct().getProductName());
                    dto.setProductCode(item.getProduct().getProductCode());
                    dto.setTotalPrice(item.getTotalPrice());
                    return dto;
                })
                .toList();

        PaymentResponse.PaymentResultDTO result = new PaymentResponse.PaymentResultDTO();
        result.setItems(items);
        cartService.updateTotalPrice(cartId);
        return result;
    }

    @Transactional
    public PaymentResponse.SingleRefundDTO singleRefund(
            Long orderItemId, Long userId, PaymentRequest.RefundDTO req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("사용자 찾을수 없음"));
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new Exception404("아이템 찾을수 없음"));
        Payment payment = paymentRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("결제내역 찾을수 없음"));

        PaymentRefund refund = PaymentRefund.builder()
                .user(user)
                .payment(payment)
                .orderItem(orderItem)
                .amount(orderItem.getTotalPrice())
                .status(RefundStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        refund.refundCompleted();
        payment.payRefund();
        refundRepository.save(refund);
        paymentRepository.save(payment);

        return new PaymentResponse.SingleRefundDTO(refund);
    }


}
