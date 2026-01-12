package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.payment.Payment;
import org.example.shopping.users.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Payment> payments = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Order(Long id, User user, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void addPayment(Payment payment) {
        this.getPayments().add(payment);
    }
}
