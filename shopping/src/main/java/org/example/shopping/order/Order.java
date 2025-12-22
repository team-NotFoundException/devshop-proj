package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.payment.Payment;
import org.example.shopping.user.User;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "order_tb")
@Entity
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne
    private Payment payment;

    private Long totalPrice;

    public Order(OrderStatus orderStatus, User user, Payment payment, Long totalPrice) {
        this.orderStatus = orderStatus;
        this.user = user;
        this.payment = payment;
        this.totalPrice = payment.getAmount();
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.COMPLETE)
            throw new IllegalArgumentException("구매확정은 변경할 수 없습니다.");
        this.orderStatus = newStatus;
    }
}
