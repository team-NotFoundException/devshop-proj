package org.example.shopping.order;

import lombok.Data;
import org.example.shopping._core.utils.MoneyUtils;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    @Data
    public static class OrderListDTO {
        private Long id;
        private String orderNumber;
        private List<PaymentViewDTO> payments;
        private String orderedAt;

        public OrderListDTO(Order order) {
            this.id = order.getId();
            this.orderNumber = order.getOrderNumber();
            this.payments = order.getPayments().stream()
                    .map(payment -> {
                        PaymentViewDTO dto = new PaymentViewDTO(payment);
                        return dto;
                    })
                    .toList();
            this.orderedAt = MyDateUtil.toDateString(order.getCreatedAt());
        }
    }

    @Data
    public static class PaymentViewDTO {
        private Long id;
        private String orderAmount;
        private String productName;
        private String productCode;
        private Integer quantity;
        private String statusDisplay;
        private PaymentStatus status;
        private String thumbnailUrl;
        private Long productId;
        private PaymentMethod method;
        private String paymentKey;
        private String orderId;

        public PaymentViewDTO(Payment payment) {
            this.id = payment.getId();
            this.orderAmount = MoneyUtils.decimalFormat.format(payment.getAmount());
            this.productName = payment.getProductName();
            this.productCode = payment.getProductCode();
            this.quantity = payment.getQuantity();
            this.thumbnailUrl = payment.getProduct().getThumbnailUrl();
            if (payment.getStatus().toString().equals("SUCCESS")) {
                this.statusDisplay = "구매완료";
            }
            if (payment.getStatus().toString().equals("CONFIRMED")) {
                this.statusDisplay = "구매확정";
            }
            if (payment.getStatus().toString().equals("FAILED")) {
                this.statusDisplay = "결제실패";
            }
            if (payment.getStatus().toString().equals("REFUNDED")) {
                this.statusDisplay = "환불";
            }
            this.status = payment.getStatus();
            this.productId = payment.getProduct().getId();
            this.method = payment.getMethod();
            this.paymentKey = payment.getPaymentKey();
            this.orderId = payment.getOrderId();
        }
    }

    @Data
    public static class OrderDetailDTO {

        private Long id;
        private List<PaymentViewDTO> payments;
        private String orderAmount;
        private String totalAmount;
        private PaymentMethod method;
        private String approvedAt;
        private String statusDisplay;

        public OrderDetailDTO(Order order) {
            this.id = order.getId();

           this.payments = order.getPayments().stream()
                    .map(payment -> {
                        PaymentViewDTO dto = new PaymentViewDTO(payment);
                        return dto;
                    })
                    .toList();


            Long amount = order.getPayments().stream()
                    .filter(payment -> payment.getStatus() != PaymentStatus.REFUNDED && payment.getStatus() != PaymentStatus.FAILED)
                    .mapToLong(Payment::getAmount)
                    .sum();
            this.totalAmount = MoneyUtils.decimalFormat.format(amount);


            this.method = order.getPayments().stream()
                    .map(Payment::getMethod)
                    .findFirst()
                    .orElse(null);

            LocalDateTime approvedAt = order.getPayments().stream()
                    .map(Payment::getApprovedAt)
                    .findFirst()
                    .orElse(null);

            this.approvedAt = MyDateUtil.toDateString(approvedAt);


        }
    }
}