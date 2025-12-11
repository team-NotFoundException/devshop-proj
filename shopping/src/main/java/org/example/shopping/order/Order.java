package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.utils.BaseTimeEntity;

@Data
@NoArgsConstructor
@Table(name = "order_tb")
@Entity
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String user;

    OrderStatus orderStatus;

    String paymentInfo;

    Long totalPrice;

    @Builder
    public Order(Long id, OrderStatus orderStatus, String paymentInfo, Long totalPrice, String user) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.paymentInfo = paymentInfo;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.COMPLETE)
            throw new IllegalArgumentException("구매확정은 변경할 수 없습니다.");
        this.orderStatus = newStatus;
    }
}
