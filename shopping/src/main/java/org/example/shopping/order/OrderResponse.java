package org.example.shopping.order;

import lombok.Data;
import org.example.shopping.payment.Payment;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class OrderResponse {

    @Data
    public static class OrderListDTO {
        private Long id;
        private List<Payment> payments;
        private Timestamp orderedAt;

        public OrderListDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();
            this.orderedAt = order.getCreatedAt();
        }
    }

//    @Data
//    public static class OrderDetailDTO {
//        DecimalFormat decimalFormat = new DecimalFormat();
//
//        private Long id;
//        private String productName;
//        private String productPrice;
//        private String status;
//        private String method;
//        private String paymentKey;
//        private String amount;
//        private String approvedAt;
//
//        public OrderDetailDTO(Payment payment) {
//            this.id = payment.getId();
//            this.productName = payment.getProductName();
//            this.productPrice = decimalFormat.format(payment.getAmount());
//            this.status = payment.getStatus().toString();
//            this.method = payment.getMethod().toString();
//            this.paymentKey = payment.getPaymentKey();
//            this.approvedAt = payment.getCreatedAt().toString();
//        }
//    }

    @Data
    public static class OrderDetailDTO {
        DecimalFormat decimalFormat = new DecimalFormat();

        private Long id;
        private List<Payment> payments;

        public OrderDetailDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();
        }
    }

}