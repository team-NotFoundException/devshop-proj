package org.example.shopping.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.paymentEnum.RefundStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private String username;
    private String orderId;
    private String paymentKey;
    private Long amount;

    private Long productId;
    private Integer quantity;
    PaymentMethod method;
    PaymentStatus status;
    private String productCode;
    private String productName;
    // private Long userPointBalance;
    private String createdAt;
    LocalDateTime requestedAt;
    LocalDateTime approvedAt;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.orderId = payment.getOrderId();
        this.paymentKey = payment.getPaymentKey();
        this.amount = payment.getAmount();
        this.quantity = payment.getQuantity();
        this.method = payment.getMethod();
        this.status = payment.getStatus();
        this.productId = payment.getProductId();
        this.productCode = payment.getProductCode();
        this.productName = payment.getProductName();
        this.createdAt = String.valueOf(payment.getCreatedAt());
        this.requestedAt = LocalDateTime.now();
        this.approvedAt = LocalDateTime.now();
    }
    public PaymentResponse() {
    }

    @Data
    @AllArgsConstructor
    public static class CartPaymentDTO {
        private Long cartId;
        private List<CartItemPaymentDTO> items;
        private Long totalAmount;
    }

    @Data
    @AllArgsConstructor
    public static class CartItemPaymentDTO {
        private Long cartItemId;
        private Long productId;
        private String productCode;
        private String productName;
        private Integer quantity;
        private Long amount;
        private Long totalPrice;
    }

    @Data
    public static class PaymentResultDTO{
        private List<PaymentItemDTO> items;

        @Data
        public static class PaymentItemDTO{
            private String productName;
            private String productCode;
            private Long totalPrice;
        }
    }



    @Data
    public static class SingleRefundDTO {
        private Long userId;
        private Long orderItemId;
//        private String orderId;
        private String paymentKey;
        private Long amount;
        PaymentMethod method;
        RefundStatus status;
        private String productCode;
        private String productName;

        public SingleRefundDTO(PaymentRefund refund){
            this.userId = refund.getUser().getId();
//            this.orderItemId = refund.getOrderItem().getId();
//            this.orderId = refund.getPayment().getOrderId();
            this.paymentKey = refund.getPayment().getPaymentKey();
//            this.amount = refund.getOrderItem().getTotalPrice();
            this.method = refund.getPayment().getMethod();
            this.status = refund.getStatus();
            this.productCode = refund.getPayment().getProductCode();
            this.productName = refund.getPayment().getProductName();
        }

    }


}
