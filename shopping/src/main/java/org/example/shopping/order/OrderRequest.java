package org.example.shopping.order;


import lombok.Data;
import org.example.shopping.user.User;


public class OrderRequest {

    @Data
    public static class CreateDTO {
        private User user;
        private Long totalPrice;

        public Order toEntity(User user) {
            return new Order(user, totalPrice);
        }
    }
}