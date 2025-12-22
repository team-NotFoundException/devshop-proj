package org.example.shopping.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    //        private String username;
    private String orderId;
    private String paymentKey;
    private Long amount;
    PaymentMethod method;
    PaymentStatus status;
    private String productCode;
    private String productName;
    // private Long userPointBalance;
    LocalDateTime requestedAt;
    LocalDateTime approvedAt;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
//            this.username = payment.getUsername();
        this.orderId = payment.getOrderId();
        this.paymentKey = payment.getPaymentKey();
        this.amount = payment.getAmount();
        this.method = payment.getMethod();
        this.status = payment.getStatus();
        this.productCode = payment.getProductCode();
        this.productName = payment.getProductName();
        this.requestedAt = LocalDateTime.now();
        this.approvedAt = LocalDateTime.now();
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
    public static class PaymentRefund {
        private Long id;
        //        private String username;
        private String orderId;
        private String paymentKey;
        private Long amount;
        PaymentMethod method;
        PaymentStatus status;
        private String productCode;
        private String productName;
        // private Long userPointBalance;
        LocalDateTime requestedAt;
        LocalDateTime approvedAt;


    }


}
