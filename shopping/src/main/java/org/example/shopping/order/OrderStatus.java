package org.example.shopping.order;

public enum OrderStatus {
    PREPARING("상품 준비중"),
    SHIPPING("배송중"),
    COMPLETE("구매확정"),
    REMAND("반품"),
    EXCHANGE("교환"),
    CANCEL("주문 취소");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
