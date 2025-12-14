package org.example.shopping.order;


import lombok.Data;
import org.example.shopping.orderItem.OrderItem;

import java.util.List;

public class OrderRequest {

    @Data
    public static class CreateDTO {
        private String user;
        private List<OrderItem> items;
        private OrderStatus orderStatus;
        private String paymentInfo;
        private Long totalPrice;



        public Order toEntity(String user) {
            return new Order(items, orderStatus, paymentInfo, totalPrice);
        }
    }
}
