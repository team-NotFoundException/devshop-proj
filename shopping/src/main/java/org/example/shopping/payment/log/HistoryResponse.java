package org.example.shopping.payment.log;

import lombok.Data;

public class HistoryResponse {
    @Data
    public static class ListDTO {
        private Long id;
        private Long userId;
        private String username;
        private String email;
        private Long paymentId;
        private Long productId;
        private String productName;
        private String productCode;
        private Integer quantity;
        private Long amount;
        private String method;
        private String oldValue;
        private String newValue;
        private String reason;
        private String createdAt;
        private String updatedAt;

        public ListDTO(History history) {
            this.id = history.getId();
            this.userId = history.getUser().getId();
            this.username = history.getUsername();
            this.email = history.getEmail();
            this.paymentId = history.getPayment().getId();
            this.productId = history.getProductId();
            this.productName = history.getProductName();
            this.productCode = history.getProductCode();
            this.quantity = history.getQuantity();
            this.amount = history.getAmount();
            this.method = history.getMethod().name();
            if (history.getOld_value() == null) {
                this.oldValue = null;
            } else {
                this.oldValue = history.getOld_value();
            }

            this.newValue = history.getNew_value();
            if (history.getReason() == null) {
                this.reason = null;
            } else {
                this.reason = history.getReason();
            }
            this.createdAt = history.getCreatedAt().toString();
            if (history.getUpdateAt() == null) {
                this.updatedAt = "-";
            } else {
                this.updatedAt = history.getUpdateAt().toString();
            }

        }
    }
}
