package org.example.shopping.payment.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.PaymentRefundRepository;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.error.BusinessException;
import org.example.shopping.payment.error.ErrorCode;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
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
    private final UserRepository userRepository;


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
    public PaymentResponse createPayment(PaymentRequest.CreateDTO createDTO, User user, Long cartId) {
        switch (createDTO.getMethod()){
            case MOCK -> new PaymentResponse(processMockPayment(user,cartId ,createDTO));
            case TOSS_PAY -> throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        return new PaymentResponse();
    }

    // 목 결제

    public Payment processMockPayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {

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
        return new Payment();
    }

    // toss 결제
    @Transactional
    public PaymentResponse.PaymentResultDTO approvePayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        List<CartItem> checkItem = getChecked(cartId);
        PaymentGateway gateway = gatewayResolver.resolve(createDTO.getMethod());
        PaymentResult paymentResult = gateway.approve(createDTO);

        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .orderId(createDTO.getOrderId() + "-" + item.getId())
                    .paymentKey(createDTO.getPaymentKey())
                    .amount(item.getTotalPrice())
                    .method(createDTO.getMethod())
                    .status(paymentResult.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                    .productCode(item.getProduct().getProductCode())
                    .productName(item.getProduct().getProductName())
                    .failureCode(paymentResult.getFailureCode())
                    .failureMessage(paymentResult.getFailureMessage())
                    .build();

            if (paymentResult.isSuccess()) {
                payment.paySuccess();
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
        return result;
    }


    @Transactional
    public PaymentResponse.SingleRefundDTO singleRefund(
            Long orderItemId, Long userId, PaymentRequest.RefundDTO req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("사용자 찾을수 없음"));

        Payment payment = paymentRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("결제내역 찾을수 없음"));

        PaymentRefund refund = PaymentRefund.builder()
                .user(user)
                .payment(payment)

                .status(RefundStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        refund.refundCompleted();
        payment.payRefund();
        refundRepository.save(refund);
        paymentRepository.save(payment);

        return new PaymentResponse.SingleRefundDTO(refund);
    }

    private String generateMerchantUid() {
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }


    public List<PaymentResponse> paymentList(Long userId) {
        List<Payment> paymentList = paymentRepository.findInfoUserId(userId);

        return paymentList.stream().map(PaymentResponse::new).toList();
    }


}
