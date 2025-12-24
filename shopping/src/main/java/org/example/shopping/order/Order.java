package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.payment.Payment;
import org.example.shopping.users.User;

@Data
@NoArgsConstructor
@Table(name = "order_tb")
@Entity
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long totalPrice;

    public Order(User user, Long totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }
}