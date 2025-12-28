package org.example.shopping.order;

import lombok.Data;
import org.example.shopping.cart.Cart;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.product.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    @Data
    public static class OrderListDTO {
        private Long orderId;
        private LocalDateTime createdAt;
        private List<OrderItemListDTO> orderItemList;

        public OrderListDTO(Long orderId, LocalDateTime createdAt, List<OrderItemListDTO> orderItemList) {
            this.orderId = orderId;
            this.createdAt = createdAt;
            this.orderItemList = orderItemList;
        }
    }

    @Data
    public static class OrderItemListDTO {
        private Long id;
        private Product product;
        private String productName;
        private Long productPrice;
        private Integer quantity;
        private OrderStatus orderStatus;
        private Long totalPrice;

        public OrderItemListDTO(Long id, Product product, String productName, Long productPrice, Integer quantity, OrderStatus orderStatus, Long totalPrice) {
            this.id = id;
            this.product = product;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
            this.orderStatus = orderStatus;
            this.totalPrice = totalPrice;
        }
    }
}
