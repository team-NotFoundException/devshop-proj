package org.example.shopping.order;

import lombok.Data;
import org.example.shopping._core.utils.MoneyUtils;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.paymentEnum.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    @Data
    public static class OrderListDTO {
        private Long id;
        private List<Payment> payments;
        private String orderAmount;
        private String orderedAt;

        public OrderListDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();
            Long PaymentAmount = order.getPayments().stream()
                    .map(Payment::getAmount)
                    .findFirst()
                    .orElse(null);
            this.orderAmount = MoneyUtils.decimalFormat.format(PaymentAmount);
            this.orderedAt = MyDateUtil.toDateString(order.getCreatedAt());
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