package org.example.shopping.payment.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cart.CartService;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.PaymentRefundRepository;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.payment.service.gateway.PaymentGateway;
import org.example.shopping.payment.service.gateway.PaymentGatewayResolver;
import org.example.shopping.payment.service.gateway.PaymentResult;
import org.example.shopping.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayResolver gatewayResolver;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;


    // ================== 카트 정보 가져오기 ================
    @Transactional(readOnly = true)
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


    // ===================== 결제 =====================
    @Transactional
    public void createPayment(
            User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        switch (createDTO.getMethod()) {
            case MOCK -> processMockPayment(sessionUser, cartId, createDTO);
            case TOSS_PAY -> {
                return;
            }
        }
    }

    private void processMockPayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        List<CartItem> checkItem = getChecked(cartId);
        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .cart(item.getCart().getId())
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
    public void approvePayment(PaymentRequest.ApproveDTO approveDTO) {
//        PaymentGateway gateway = gatewayResolver.resolve(approveDTO.getMethod());
//        PaymentResult result = gateway.approve(approveDTO);
//
//        Payment payment = Payment.builder()
////                .username(username)
//                .orderId(approveDTO.getOrderId())
//                .paymentKey(approveDTO.getPaymentKey())
//                .amount(approveDTO.getAmount())
//                .method(approveDTO.getMethod())
//                .status(result.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
//                .productCode(approveDTO.getProductCode())
//                .productName(approveDTO.getProductName())
//                .failureCode(result.getFailureCode())
//                .failureMessage(result.getFailureMessage())
//                .build();
//        if (result.isSuccess()) {
//            payment.paySuccess();
//        }
//        paymentRepository.save(payment);
    }



    public PaymentResponse refundPaymentForm(Long id
            , Long sessionUserId
    ) {
        Payment paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception404("찾을 수 없음"));

        return new PaymentResponse(paymentEntity);
    }

    @Transactional
    public PaymentRefund refundPayment(Long id, PaymentRequest.RefundDTO refundDTO
            , Long sessionUserId
    ) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception404("결제 내역 찾을 수 없음"));

        PaymentRefund refund = PaymentRefund.builder()
                .payment(payment)

                .amount(refundDTO.getAmount())
                .reason(refundDTO.getReason())
                .status(RefundStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        refund.refundCompleted();
        payment.payRefund();

        refundRepository.save(refund);
        paymentRepository.save(payment);

return refund;

    }
}
