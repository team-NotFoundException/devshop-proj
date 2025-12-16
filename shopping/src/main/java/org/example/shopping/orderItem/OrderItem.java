package org.example.shopping.orderItem;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.order.Order;
import org.example.shopping._core.utils.BaseTimeEntity;

@Data
@NoArgsConstructor
@Table(name = "order_item_tb")
@Entity
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Order order;

    String product;

    Integer quantity;

    Long orderPrice;

    Long totalPrice;

    public OrderItem(Order order, Long orderPrice, String product, Integer quantity, Long totalPrice) {
        this.order = order;
        this.orderPrice = orderPrice;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
