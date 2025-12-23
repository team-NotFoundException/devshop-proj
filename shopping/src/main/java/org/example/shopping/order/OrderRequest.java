package org.example.shopping.order;


import lombok.Data;
import org.example.shopping.payment.Payment;
import org.example.shopping.users.User;

public class OrderRequest {

    @Data
    public static class CreateDTO {
        private User user;
        private OrderStatus orderStatus;
        private Payment payment;
        private Long totalPrice;

        public Order toEntity(User user) {
            return new Order(orderStatus, user, payment, totalPrice);
        }
    }

}