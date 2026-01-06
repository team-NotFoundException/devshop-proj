package org.example.shopping.orderItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping.order.Order;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.order.OrderStatus;
import org.example.shopping.payment.Payment;
import org.example.shopping.product.Product;

@Data
@NoArgsConstructor
@Table(name = "order_item_tb")
@Entity
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String productName;

    private Long productPrice;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long totalPrice;

    private Boolean isComplete;

    private Boolean isRefund;

    @Builder
    public OrderItem(Payment payment, Product product, String productName, Long productPrice, Integer quantity, Long totalPrice) {
        this.payment = payment;
        this.product = product;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.orderStatus = OrderStatus.PREPARING;
        this.totalPrice = totalPrice;
        this.isComplete = false;
        this.isRefund = false;
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.COMPLETE)
            throw new IllegalArgumentException("구매확정은 변경할 수 없습니다.");
        this.orderStatus = newStatus;
    }

    public void confirmStatus() {
        if (this.orderStatus == OrderStatus.REMAND) {
            throw new Exception400("반품중인 상품입니다.");
        }

        this.orderStatus = OrderStatus.COMPLETE;
        this.isComplete = true;
        this.isRefund = false;
    }

    public void refundStatus() {
        if (this.orderStatus == OrderStatus.COMPLETE) {
            throw new Exception400("구매확정된 상품입니다.");
        }

        this.orderStatus = OrderStatus.REMAND;
        this.isRefund = true;
        this.isComplete = false;
    }
}