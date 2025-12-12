package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.utils.BaseTimeEntity;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "order_tb")
@Entity
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String user;

    @OneToMany(mappedBy = "order")
    List<OrderItem> items;

    OrderStatus orderStatus;

    String paymentInfo;

    Long totalPrice;

    public Order(String user, List<OrderItem> items, OrderStatus orderStatus, String paymentInfo, Long totalPrice) {
        this.user = user;
        this.items = items;
        this.orderStatus = orderStatus;
        this.paymentInfo = paymentInfo;
        this.totalPrice = totalPrice;
    }

    public void updateOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.COMPLETE)
            throw new IllegalArgumentException("구매확정은 변경할 수 없습니다.");
        this.orderStatus = newStatus;
    }
}
