package org.example.shopping.payment.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cart.Cart;
import org.example.shopping.cart.CartRepository;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.cartItem.CartItemRepository;
import org.example.shopping.order.Order;
import org.example.shopping.order.OrderRepository;
import org.example.shopping.order.OrderService;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.PaymentRefundRepository;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.error.BusinessException;
import org.example.shopping.payment.error.ErrorCode;
import org.example.shopping.payment.log.Field;
import org.example.shopping.payment.log.History;
import org.example.shopping.payment.log.HistoryRepository;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.payment.service.gateway.PaymentGateway;
import org.example.shopping.payment.service.gateway.PaymentGatewayResolver;
import org.example.shopping.payment.service.gateway.PaymentResult;
import org.example.shopping.product.Product;
import org.example.shopping.product.ProductRepository;
import org.example.shopping.users.MailService;
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
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final HistoryRepository historyRepository;
    private final ProductRepository productRepository;
    private final MailService mailService;


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

    // ===================== 결제 후 수량 증가 감소 메서드 =====================
    @Transactional
    public void decreaseQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception404("NOT FOUND"));
        product.decreaseQuantity(quantity);
        productRepository.save(product);
    }

    // ===================== 환불 후 수량 증가 감소 메서드 =====================
    @Transactional
    public void increaseQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception404("NOT FOUND"));
        product.increaseQuantity(quantity);
        productRepository.save(product);
    }

    // ===================== 결제 =====================
    @Transactional
    public PaymentResponse createPayment(PaymentRequest.CreateDTO createDTO, User user, Long cartId) {
        switch (createDTO.getMethod()) {
            case MOCK -> new PaymentResponse(processMockPayment(user, cartId, createDTO));
            case TOSS_PAY -> throw new BusinessException(ErrorCode.INVALID_INPUT, "TossPay는 approvePayment 메서드를 사용하세요");
        }
        return new PaymentResponse();
    }

    // 목 결제

    public Payment processMockPayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        Order order = orderService.createOrder(sessionUser.getId());

        List<CartItem> checkItem = getChecked(cartId);
        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .orderId("ORD-" + UUID.randomUUID())
                    .paymentKey("MOCK-" + UUID.randomUUID())
                    .amount(item.getTotalPrice())
                    .quantity(item.getQuantity())
                    .productId(item.getProduct().getId())
                    .method(createDTO.getMethod())
                    .status(PaymentStatus.SUCCESS)
                    .productCode(item.getProduct().getProductCode())
                    .productName(item.getProduct().getProductName())
                    .build();
            payment.paySuccess();
            Payment save = paymentRepository.save(payment);
            order.addPayment(payment);
            paymentHistory(save, sessionUser, null, save.getStatus().name(), Field.STATUS, null);
            decreaseQuantity(save.getProductId(), save.getQuantity());

        }
        mailService.sendPayInfo(sessionUser, order.getId());
        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));
        cart.clearItems();
        cart.updateAmount();

        return new Payment();
    }

    // toss 결제
    @Transactional
    public PaymentResponse.PaymentResultDTO approvePayment(User sessionUser, Long cartId, PaymentRequest.CreateDTO createDTO) {
        List<CartItem> checkItem = getChecked(cartId);
        PaymentGateway gateway = gatewayResolver.resolve(createDTO.getMethod());
        PaymentResult paymentResult = gateway.approve(createDTO);
        Order order = orderService.createOrder(sessionUser.getId());

        for (CartItem item : checkItem) {
            Payment payment = Payment.builder()
                    .user(sessionUser)
                    .orderId(createDTO.getOrderId() + "-" + item.getId())
                    .paymentKey(createDTO.getPaymentKey())
                    .amount(item.getTotalPrice())
                    .quantity(item.getQuantity())
                    .method(createDTO.getMethod())
                    .productId(item.getProduct().getId())
                    .status(paymentResult.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                    .productCode(item.getProduct().getProductCode())
                    .productName(item.getProduct().getProductName())
                    .failureCode(paymentResult.getFailureCode())
                    .failureMessage(paymentResult.getFailureMessage())
                    .build();

            if (paymentResult.isSuccess()) {
                payment.paySuccess();
            }
            Payment save = paymentRepository.save(payment);
            paymentHistory(save, sessionUser, null, save.getStatus().name(), Field.STATUS, null);

            decreaseQuantity(save.getProductId(), save.getQuantity());
            order.addPayment(payment);
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

        Cart cart = cartRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다."));
        cart.clearItems();
        cart.updateAmount();
        mailService.sendPayInfo(sessionUser, order.getId());
        PaymentResponse.PaymentResultDTO result = new PaymentResponse.PaymentResultDTO();
        result.setItems(items);
        return result;
    }


    @Transactional
    public PaymentResponse.SingleRefundDTO singleRefund(
            Long paymentId, Long userId, PaymentRequest.RefundDTO req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("사용자 찾을수 없음"));

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception404("결제내역 찾을수 없음"));

        History history = historyRepository.findByPaymentId(paymentId).orElseThrow(() -> new Exception404("없음"));

        String old_value = payment.getStatus().name();

        Long refundAmount = (req.getAmount() != null) ? req.getAmount() : payment.getAmount();
        String refundReason = (req.getReason() != null) ? req.getReason() : "고객요청";

        PaymentRequest.RefundDTO refundDTO = new PaymentRequest.RefundDTO();
        refundDTO.setPaymentKey(payment.getPaymentKey());
        refundDTO.setAmount(refundAmount);
        refundDTO.setReason(refundReason);

        PaymentGateway gateway = gatewayResolver.resolve(payment.getMethod());
        PaymentResult result = gateway.refund(refundDTO);

        PaymentRefund refund = PaymentRefund.builder()
                .user(user)
                .payment(payment)
                .amount(req.getAmount())
                .reason(req.getReason())
                .status(RefundStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();
        if (result.isSuccess()) {
            refund.refundCompleted();
            payment.payRefund();
        } else {
            refund.refundFailed(result.getFailureCode(), result.getFailureMessage());
        }
        PaymentRefund reason = refundRepository.save(refund);
        Payment save = paymentRepository.save(payment);

        paymentHistory(save, user, old_value, save.getStatus().name(), Field.STATUS, reason.getReason());
        if (old_value.equals(history.getNew_value())) {
            historyRepository.delete(history);
        }

        increaseQuantity(save.getProductId(), save.getQuantity());

        return new PaymentResponse.SingleRefundDTO(refund);
    }

    @Transactional
    public void confirmPurchase(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception404("결제내역 찾을수 없음"));
        History history = historyRepository.findByPaymentId(paymentId).orElseThrow(() -> new Exception404("기록없음"));
        String old_value = payment.getStatus().name();

        payment.confirm();
        paymentHistory(payment, payment.getUser(), old_value, payment.getStatus().name(), Field.STATUS, null);
        if (old_value.equals(history.getNew_value())) {
            historyRepository.delete(history);
        }
    }

    // =============== History HelperMethod =================

    private void paymentHistory(Payment payment, User user, String old_value, String new_value, Field field, String reason) {
        History history = History.builder()
                .user(user)
                .payment(payment)
                .email(user.getEmail())
                .username(user.getUsername())
                .field(field)
                .old_value(old_value)
                .new_value(new_value)
                .productId(payment.getProductId())
                .productCode(payment.getProductCode())
                .productName(payment.getProductName())
                .amount(payment.getAmount())
                .quantity(payment.getQuantity())
                .method(payment.getMethod())
                .reason(reason)
                .build();
        historyRepository.save(history);
    }
}
