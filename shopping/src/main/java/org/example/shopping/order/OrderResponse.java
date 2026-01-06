package org.example.shopping.order;

import lombok.Data;
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
        private String orderStatus;
        private Long totalPrice;
        private Boolean isComplete;
        private Boolean isRefund;

        public OrderItemListDTO(Long id, Product product, String productName, Long productPrice, Integer quantity, OrderStatus orderStatus, Long totalPrice, Boolean isComplete, Boolean isRefund) {
            this.id = id;
            this.product = product;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
            this.orderStatus = orderStatus.getLabel();
            this.totalPrice = totalPrice;
            this.isComplete = isComplete;
            this.isRefund = isRefund;
        }
    }

    @Data
    public static class OrderDetailDTO {
        private Long id;
        private Product product;
        private String productName;
        private Long productPrice;
        private Integer quantity;
        private String orderStatus;
        private Long totalPrice;
        private Boolean isComplete;
        private Boolean isRefund;
        private String method;
        private String paymentKey;
        private Long amount;
        private LocalDateTime approvedAt;

        public OrderDetailDTO(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.product = orderItem.getProduct();
            this.productName = orderItem.getProductName();
            this.productPrice = orderItem.getProductPrice();
            this.quantity = orderItem.getQuantity();
            this.orderStatus = orderItem.getOrderStatus().getLabel();
            this.totalPrice = orderItem.getTotalPrice();
            this.isComplete = orderItem.getIsComplete();
            this.isRefund = orderItem.getIsRefund();
            this.method = orderItem.getPayment().getMethod().toString();
            this.paymentKey = orderItem.getPayment().getPaymentKey();
            this.amount = orderItem.getPayment().getAmount();
            this.approvedAt = orderItem.getPayment().getApprovedAt();
        }
    }
}
