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
        private List<Payment> payments;
        private String orderAmount;
        private String orderedAt;
        private String statusDisplay;

        public OrderListDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();
            Long PaymentAmount = order.getPayments().stream()
                    .map(Payment::getAmount)
                    .findFirst()
                    .orElse(null);
            this.orderAmount = MoneyUtils.decimalFormat.format(PaymentAmount);
            this.orderedAt = MyDateUtil.toDateString(order.getCreatedAt());
            PaymentStatus status = order.getPayments().stream()
                    .map(Payment::getStatus)
                    .findFirst()
                    .orElse(null);
            if (status.toString().equals("SUCCESS")) {
                this.statusDisplay = "구매완료";
            }
            if (status.toString().equals("CONFIRMED")) {
                this.statusDisplay = "구매확정";
            }
            if (status.toString().equals("FAILED")) {
                this.statusDisplay = "결제실패";
            }
            if (status.toString().equals("REFUNDED")) {
                this.statusDisplay = "환불";
            }
        }
    }

    @Data
    public static class OrderDetailDTO {

        private Long id;
        private List<Payment> payments;
        private String orderAmount;
        private String totalAmount;
        private PaymentMethod method;
        private String approvedAt;

        public OrderDetailDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();

            Long PaymentAmount = order.getPayments().stream()
                    .map(Payment::getAmount)
                    .findFirst()
                    .orElse(null);
            this.orderAmount = MoneyUtils.decimalFormat.format(PaymentAmount);

            Long amount = order.getPayments().stream()
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