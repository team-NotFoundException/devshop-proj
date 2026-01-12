package org.example.shopping.order;

import org.example.shopping.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.payments WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findAllByUserIdAndOrderByCreatedAtDesc(Long userId);

    Optional<Order> findByUserId(Long userId);
}
