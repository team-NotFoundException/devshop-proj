package org.example.shopping.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.payment.Payment;

import java.sql.Timestamp;
import java.util.List;

public class OrderResponse {

    @Data
    @AllArgsConstructor
    public static class OrderListDTO {
        private Long id;
        private List<Payment> payments;
        private String createdAt;

        public OrderListDTO(Order order) {
            this.id = order.getId();
            this.payments = order.getPayments();
            this.createdAt = MyDateUtil.timestampFormat(order.getCreatedAt());
        }
    }
}