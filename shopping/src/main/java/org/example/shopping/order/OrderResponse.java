package org.example.shopping.order;

import lombok.Data;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.product.Product;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderResponse {

    @Data
    public static class OrderListDTO {
        private Long orderId;
        private String orderNumber;
        private String createdAt;
        private List<OrderItemListDTO> orderItemList;

        public OrderListDTO(Long orderId, String orderNumber, LocalDateTime createdAt, List<OrderItemListDTO> orderItemList) {
            this.orderId = orderId;
            this.orderNumber = orderNumber;
            this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.orderItemList = orderItemList;
        }
    }

    @Data
    public static class OrderItemListDTO {
        DecimalFormat decimalFormat = new DecimalFormat();

        private Long id;
        private Product product;
        private String productName;
        private String productPrice;
        private Integer quantity;
        private String orderStatus;
        private String totalPrice;
        private Boolean isComplete;
        private Boolean isRefund;

        public OrderItemListDTO(Long id, Product product, String productName, Long productPrice, Integer quantity, OrderStatus orderStatus, Long totalPrice, Boolean isComplete, Boolean isRefund) {
            this.id = id;
            this.product = product;
            this.productName = productName;
            this.productPrice = decimalFormat.format(productPrice);
            this.quantity = quantity;
            this.orderStatus = orderStatus.getLabel();
            this.totalPrice = decimalFormat.format(totalPrice);
            this.isComplete = isComplete;
            this.isRefund = isRefund;
        }
    }

    @Data
    public static class OrderDetailDTO {
        DecimalFormat decimalFormat = new DecimalFormat();

        private Long id;
        private Product product;
        private String productName;
        private String productPrice;
        private Integer quantity;
        private String orderStatus;
        private String totalPrice;
        private Boolean isComplete;
        private Boolean isRefund;
        private String method;
        private String paymentKey;
        private String amount;
        private String approvedAt;

        public OrderDetailDTO(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.product = orderItem.getProduct();
            this.productName = orderItem.getProductName();
            this.productPrice = decimalFormat.format(orderItem.getProductPrice());
            this.quantity = orderItem.getQuantity();
            this.orderStatus = orderItem.getOrderStatus().getLabel();
            this.totalPrice = decimalFormat.format(orderItem.getTotalPrice());
            this.isComplete = orderItem.getIsComplete();
            this.isRefund = orderItem.getIsRefund();
            this.method = orderItem.getPayment().getMethod().toString();
            this.paymentKey = orderItem.getPayment().getPaymentKey();
            this.amount = decimalFormat.format(orderItem.getPayment().getAmount());
            this.approvedAt = orderItem.getPayment().getApprovedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
}
