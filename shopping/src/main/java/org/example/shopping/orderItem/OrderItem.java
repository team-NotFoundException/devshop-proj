package org.example.shopping.orderItem;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Order order;

    @OneToOne
    private Payment payment; // 가격, 수량,

    private Integer quantity;

    @OneToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long totalPrice;

    @Builder
    public OrderItem(Order order, Payment payment) {
        this.order = order;
        this.orderStatus = OrderStatus.PREPARING;
        this.payment = payment;
        this.totalPrice = payment.getAmount();
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.COMPLETE)
            throw new IllegalArgumentException("구매확정은 변경할 수 없습니다.");
        this.orderStatus = newStatus;
    }
}